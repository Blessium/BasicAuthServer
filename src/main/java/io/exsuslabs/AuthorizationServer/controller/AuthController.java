package io.exsuslabs.AuthorizationServer.controller;

import io.exsuslabs.AuthorizationServer.requests.CredentialUserRequest;
import io.exsuslabs.AuthorizationServer.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@ComponentScan(basePackageClasses = AuthenticationService.class)
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(
            value = "/login",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> login(@Validated @RequestBody CredentialUserRequest credentialUserRequest, BindingResult bindingResult) {
        Map<String, String> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            result.put("message", "binding error");
            result.put("status", "error");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        Optional<String> jwtToken = authenticationService.validateCredentials(credentialUserRequest);
        if (jwtToken.isEmpty()) {
            result.put("message", "invalid user ID or password");
            result.put("status", "failed");
            return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
        }
        result.put("access_token", jwtToken.get());
        result.put("status", "success");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
