package io.exsuslabs.AuthorizationServer.service;

import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.jwt.JWTService;
import io.exsuslabs.AuthorizationServer.repository.UserRepository;
import io.exsuslabs.AuthorizationServer.utils.AccessToken;
import io.exsuslabs.AuthorizationServer.utils.Convertions;
import io.exsuslabs.AuthorizationServer.utils.ValidityThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class OAuthService {
    private static List<AccessToken> requests;
    private ValidityThread validityThread;
    @Autowired
    UserRepository userRepository;

    public OAuthService () {
        requests = new ArrayList<>();
        validityThread = new ValidityThread(requests);
        validityThread.start();
    }

    public Optional<UUID> generateRequest(String token, String clientID) {
        Optional<UserDomain> userDomain = userRepository.findById(JWTService.extractUsername(token.split(" ")[1]));
        System.out.println(userDomain.isEmpty());
        System.out.println(token.split(" ")[1]);
        if (userDomain.isEmpty()) {
            return Optional.empty();
        }
        UserDomain user = userDomain.get();
        AccessToken accessToken = new AccessToken(UUID.fromString(clientID), Duration.ofMinutes(10), user.getUsername());
        requests.add(accessToken);
        return Optional.of(accessToken.getRequestId());
    }

    public Optional<Map<String, String>> checkRequest(String clientId, String requestId) {
        for (AccessToken request: requests) {
           if (request.isEqual(UUID.fromString(requestId), UUID.fromString(clientId))) {
               Optional<UserDomain> user = userRepository.findById(request.getUsername());
               Map<String, String> userInfo = user.map(Convertions::convertUserToMap).orElse(null);
               if (Objects.isNull(userInfo)) {
                   return Optional.empty();
               }
               requests.remove(request);
               return Optional.of(userInfo);
           }
        }
        return Optional.empty();
    }
}
