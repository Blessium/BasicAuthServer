package io.exsuslabs.AuthorizationServer.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private Map<String, String> map;
    private ResponseEntity<Map<String, String>> response;
    private HttpStatus status;

    public ResponseBuilder() {
        map = new HashMap<>();
    }

    public static ResponseBuilder create() {
        return new ResponseBuilder();
    }

    public ResponseBuilder message(String message) {
        map.put("message", message);
        return this;
    }

    public ResponseBuilder errorMessage(String message) {
        map.put("error_message", message);
        return this;
    }

    public ResponseBuilder authToken(String token) {
        map.put("auth_token", token);
        return this;
    }

    public ResponseBuilder token(String token) {
        map.put("token", token);
        return this;
    }

    public ResponseBuilder userInfo(Map<String, String> user){
        user.forEach(map::putIfAbsent);
        return this;
    }

    public ResponseBuilder forbiddenStatus() {
        status = HttpStatus.FORBIDDEN;
        map.put("code", "403");
        map.put("status", "forbidden");
        return this;
    }

    public ResponseBuilder okStatus() {
        status = HttpStatus.OK;
        map.put("code", "200");
        map.put("status", "ok");
        return this;
    }

    public ResponseBuilder createdStatus() {
        status = HttpStatus.CREATED;
        map.put("code", "201");
        map.put("status", "created");
        return this;
    }

    public ResponseBuilder badRequestStatus() {
        status = HttpStatus.BAD_REQUEST;
        map.put("code", "400");
        map.put("status", "bad request");
        return this;
    }
    public ResponseEntity<Map<String, String>> build() {
        return new ResponseEntity<>(map, status);
    }
}
