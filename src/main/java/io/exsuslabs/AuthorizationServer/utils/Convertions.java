package io.exsuslabs.AuthorizationServer.utils;

import io.exsuslabs.AuthorizationServer.domain.UserDomain;

import java.util.HashMap;
import java.util.Map;

public class Convertions {

    public static Map<String, String> convertUserToMap(UserDomain userDomain) {
        Map<String, String> result = new HashMap<>();
        result.put("username", userDomain.getUsername());
        result.put("email", userDomain.getEmail());
        result.put("password", userDomain.getPassword());
        return result;
    }
}
