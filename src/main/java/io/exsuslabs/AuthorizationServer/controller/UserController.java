package io.exsuslabs.AuthorizationServer.controller;

import io.exsuslabs.AuthorizationServer.requests.FullUserRequest;
import io.exsuslabs.AuthorizationServer.service.AuthenticationService;
import io.exsuslabs.AuthorizationServer.service.AuthorizationService;
import io.exsuslabs.AuthorizationServer.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
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
        HashMap<String, String> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
                result.put("message", "binding error");
                result.put("status", "error");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        Optional<String> error = userManagementService.createUser(fullUserRequest);
        if (error.isPresent()) {
            result.put("message", error.get());
            result.put("status", "error");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            result.put("message", "user created successfully");
            result.put("status", "success");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }

    @GetMapping(
            value = "/users/{id}",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> getUserInfo(@Nullable @RequestHeader (name="Authorization") String token, @PathVariable("id") int id) {
        Map<String, String> result = new HashMap<>();
        if (Objects.isNull(token)){
            result.put("message", "not authorized");
            result.put("status", "error");
            return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
        }

        Optional<String> error = authorizationService.checkPermission(token, id);

        return null;
    }
}
