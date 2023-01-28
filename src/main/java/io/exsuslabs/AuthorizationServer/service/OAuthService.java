package io.exsuslabs.AuthorizationServer.service;

import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.jwt.JWTService;
import io.exsuslabs.AuthorizationServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class OAuthService {
    private static Map<Pair<UUID, UUID>, Long> requests;
    @Autowired
    UserRepository userRepository;

    public OAuthService () {
        requests = new HashMap<>();
    }

    public Optional<UUID> generateRequest(String token, String clientID) {
        Optional<UserDomain> userDomain = userRepository.findById(JWTService.extractUsername(token.split(" ")[1]));
        System.out.println(userDomain.isEmpty());
        System.out.println(token.split(" ")[1]);
        if (userDomain.isEmpty()) {
            return Optional.empty();
        }
        UserDomain user = userDomain.get();
        UUID accessToken = UUID.randomUUID();
        return Optional.of(accessToken);
    }

    public Optional<UserDomain> validateRequest(String access_token, UUID clientID) {
        return Optional.empty();
    }
}
