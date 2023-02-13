package io.exsuslabs.AuthorizationServer.controller;

import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.requests.AccessOAuthRequest;
import io.exsuslabs.AuthorizationServer.requests.CredentialUserRequest;
import io.exsuslabs.AuthorizationServer.service.AuthenticationService;
import io.exsuslabs.AuthorizationServer.service.AuthorizationService;
import io.exsuslabs.AuthorizationServer.service.DeveloperService;
import io.exsuslabs.AuthorizationServer.service.OAuthService;
import io.exsuslabs.AuthorizationServer.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@RestController
@ComponentScan(basePackageClasses = {AuthenticationService.class, AuthorizationService.class, DeveloperService.class, OAuthService.class})
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    DeveloperService developerService;

    @Autowired
    OAuthService oAuthService;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, String>> login(@Validated @RequestBody CredentialUserRequest credentialUserRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseBuilder.create().errorMessage("binding error").badRequestStatus().build();
        }
        Optional<String> jwtToken = authenticationService.validateCredentials(credentialUserRequest);
        if (jwtToken.isEmpty()) {
            return ResponseBuilder.create().errorMessage("invalid user ID or password").forbiddenStatus().build();
        }
        return ResponseBuilder.create().token(jwtToken.get()).okStatus().build();
    }

    @GetMapping(
            value = "/oauth2",
            produces = "application/json"
    )
    public ResponseEntity<?> oAuthentication(
            @Nullable @RequestHeader (name="Authorization") String token,
            @RequestParam("client_id") String clientID, @RequestParam("redirect_uri") String redirect_uri)
    {
        if (Objects.isNull(token)){
            return ResponseBuilder.create().errorMessage("not authorized").forbiddenStatus().build();
        }

        Optional<String> error;
        error = authorizationService.checkTokenValidity(token);
        if (error.isPresent()) {
            return ResponseBuilder.create().errorMessage(error.get()).forbiddenStatus().build();
        }

        error = developerService.verifyUrls(clientID, redirect_uri, token);

        if (error.isPresent()) {
            return ResponseBuilder.create().errorMessage(error.get()).forbiddenStatus().build();
        }

        Optional<UUID> request = oAuthService.generateRequest(token, clientID);
        if (request.isEmpty()) {
            return ResponseBuilder
                    .create()
                    .errorMessage("could not generate the access token")
                    .forbiddenStatus()
                    .build();
        }
        System.out.println(request.get());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirect_uri));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping(
            value="/oauth2",
            produces = "application/json"
    )
    public ResponseEntity<?> checkOAuth2Request(@Validated @RequestBody AccessOAuthRequest accessOAuthRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseBuilder
                    .create()
                    .errorMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage())
                    .badRequestStatus()
                    .build();
        }

        Optional<String> error;
        error = authenticationService.validateDeveloperCredentials(accessOAuthRequest.getClient_id(), accessOAuthRequest.getPassword());
        if (error.isPresent()) {
            return ResponseBuilder
                    .create()
                    .errorMessage(error.get())
                    .forbiddenStatus()
                    .build();
        }

        Optional<Map<String, String>> user = oAuthService.checkRequest(accessOAuthRequest.getClient_id(), accessOAuthRequest.getRequest_id());
        if (user.isEmpty()) {
            return ResponseBuilder
                    .create()
                    .errorMessage("the request is expired or is not valid")
                    .forbiddenStatus()
                    .build();
        }

        return ResponseBuilder
                .create()
                .userInfo(user.get())
                .okStatus()
                .build();

    }
}
