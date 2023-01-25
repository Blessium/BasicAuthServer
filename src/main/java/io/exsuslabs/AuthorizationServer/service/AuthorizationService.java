package io.exsuslabs.AuthorizationServer.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.jwt.JWTService;
import io.exsuslabs.AuthorizationServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class AuthorizationService {
    @Autowired
    UserRepository userRepository;

    public boolean validateToken(String token) {
        Optional<DecodedJWT> decodedJWT = JWTService.validateJWT(token);
        return decodedJWT.isPresent();
    }


    public boolean checkTokenType(String tokenRequest) {
        return tokenRequest.split(" ")[0].equals("Bearer");
    }

    public String getTokenString(String tokenRequest) {
        return tokenRequest.split(" ")[1];
    }

    public Optional<String> checkTokenValidity(String tokenRequest) {
        if (!validateToken(getTokenString(tokenRequest)) || !checkTokenType(tokenRequest))
            return Optional.of("you should be logged");
        return Optional.empty();
    }
    public Optional<String> checkPermission(String tokenRequest, int id) {
        String token = getTokenString(tokenRequest);

        if (!validateToken(token) || !checkTokenType(tokenRequest)){
            return Optional.of("token is not valid");
        }
        Optional<UserDomain> user = userRepository.findByUsername(JWTService.extractUsername(token));
        if (user.isEmpty() || user.get().getId() != id) {
            return Optional.of("you're not allowed to access this resource");
        }

        return Optional.empty();
    }
}
