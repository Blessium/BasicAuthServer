package io.exsuslabs.AuthorizationServer.controller;

import io.exsuslabs.AuthorizationServer.utils.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeveloperController {

    @PostMapping(
            value = "/dev",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> createDev() {
        return ResponseBuilder.create().message("developer account activated successfully").createdStatus().build();
    }
}
