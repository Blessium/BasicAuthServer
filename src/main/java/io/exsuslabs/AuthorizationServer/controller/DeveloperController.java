package io.exsuslabs.AuthorizationServer.controller;

import io.exsuslabs.AuthorizationServer.requests.DeveloperUpdateRequest;
import io.exsuslabs.AuthorizationServer.service.AuthorizationService;
import io.exsuslabs.AuthorizationServer.service.DeveloperService;
import io.exsuslabs.AuthorizationServer.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@ComponentScan( basePackageClasses = {DeveloperService.class, AuthorizationService.class})
public class DeveloperController {

    @Autowired
    DeveloperService developerService;

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping(
            value = "/dev/activation",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> createDev(
            @Nullable @RequestHeader(name="Authorization") String token
    ) {
        if (Objects.isNull(token)) {
            return ResponseBuilder
                    .create()
                    .errorMessage("you need to be authenticated")
                    .forbiddenStatus()
                    .build();
        }

        Optional<String> error = authorizationService.checkTokenValidity(token);
        if (error.isPresent()) {
            return ResponseBuilder
                    .create()
                    .errorMessage(error.get())
                    .forbiddenStatus()
                    .build();
        }

        developerService.enableDevAccount(token);

        return ResponseBuilder
                .create()
                .message("developer account activated successfully")
                .createdStatus()
                .build();
    }

    @PutMapping(
            value = "/dev",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> updateDevInfo(
            @Validated @RequestBody DeveloperUpdateRequest developerUpdateRequest,
            @RequestHeader(name="Authorization") String token,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseBuilder
                    .create()
                    .errorMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage())
                    .badRequestStatus()
                    .build();
        }


        Optional<String> error;
        error = authorizationService.checkTokenValidity(token);
        if (error.isPresent()) {
            return ResponseBuilder
                    .create()
                    .errorMessage(error.get())
                    .forbiddenStatus()
                    .build();
        }
        error = developerService.updateDevInfo(developerUpdateRequest, token);

        return ResponseBuilder
                .create()
                .message("developer informations updated successfully.")
                .okStatus()
                .build();
    }


    @DeleteMapping(
            value = "/dev/activation",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> removeDev() {
        return ResponseBuilder
                .create()
                .message("developer account deactivated successfully")
                .okStatus()
                .build();
    }
}
