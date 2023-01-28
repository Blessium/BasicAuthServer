package io.exsuslabs.AuthorizationServer.controller;

import io.exsuslabs.AuthorizationServer.jwt.JWTService;
import io.exsuslabs.AuthorizationServer.requests.FullUserRequest;
import io.exsuslabs.AuthorizationServer.service.AuthorizationService;
import io.exsuslabs.AuthorizationServer.service.UserManagementService;
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
@ComponentScan( basePackageClasses = {UserManagementService.class, AuthorizationService.class})
public class UserController {

    @Autowired
    UserManagementService userManagementService;
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping(
            value = "/users",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> createUser(@Validated @RequestBody FullUserRequest fullUserRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
                return ResponseBuilder.create().errorMessage("Binding Error").badRequestStatus().build();
        }
        Optional<String> error = userManagementService.createUser(fullUserRequest);
        if (error.isPresent()) {
            return ResponseBuilder.create().errorMessage(error.get()).badRequestStatus().build();
        } else {
            return ResponseBuilder.create().message("user created successfully").createdStatus().build();
        }
    }

    @GetMapping(
            value = "/users/me",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> getUserInfo(@Nullable @RequestHeader (name="Authorization") String token) {
        Map<String, String> result = new HashMap<>();
        if (Objects.isNull(token)){
            return ResponseBuilder.create().errorMessage("you're not authorized").forbiddenStatus().build();
        }

        Optional<String> error = authorizationService.checkTokenValidity(token);
        if (error.isPresent()) {
            return ResponseBuilder.create().errorMessage(error.get()).forbiddenStatus().build();
        }

        return ResponseBuilder.create()
                .userInfo(userManagementService.getUserInfo(JWTService.extractUsername(authorizationService.getTokenString(token))))
                .okStatus()
                .build();
    }
}
