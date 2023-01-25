package io.exsuslabs.AuthorizationServer.controller;

import io.exsuslabs.AuthorizationServer.requests.CredentialUserRequest;
import io.exsuslabs.AuthorizationServer.service.AuthenticationService;
import io.exsuslabs.AuthorizationServer.service.AuthorizationService;
import io.exsuslabs.AuthorizationServer.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@ComponentScan(basePackageClasses = {AuthenticationService.class, AuthorizationService.class})
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping(
            value = "/login",
            consumes = "application/json",
            produces = "application/json"
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
            value = "/auth",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> oAuthentication(
            @Nullable @RequestHeader (name="Authorization") String token,
            @RequestParam String clientID, @RequestParam String redirect_uri)
    {
        if (Objects.isNull(token)){
            return ResponseBuilder.create().errorMessage("not authorized").forbiddenStatus().build();
        }

        Optional<String> error = authorizationService.checkTokenValidity(token);
        if (error.isPresent()) {
            return ResponseBuilder.create().message(error.get()).forbiddenStatus().build();
        }



        return null;
    }

}
