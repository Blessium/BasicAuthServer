package io.exsuslabs.AuthorizationServer.service;

import io.exsuslabs.AuthorizationServer.domain.DeveloperDomain;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.jwt.JWTService;
import io.exsuslabs.AuthorizationServer.repository.DeveloperRepository;
import io.exsuslabs.AuthorizationServer.repository.UserRepository;
import io.exsuslabs.AuthorizationServer.requests.DeveloperUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeveloperService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeveloperRepository developerRepository;

    public Optional<String> enableDevAccount(String token) {
        String username = JWTService.extractUsername(token.split(" ")[1]);
        Optional<UserDomain> user = userRepository.findById(username);
        if (user.isEmpty()) {
            return Optional.of("could not create dev account");
        }
        Optional<DeveloperDomain> check = developerRepository.findByUser(user.get());
        if (check.isPresent()) {
            return Optional.of("user is already developer");
        }
        DeveloperDomain dev = new DeveloperDomain();
        dev.setUser(user.get());
        developerRepository.save(dev);
        return Optional.empty();
    }

    public Optional<String> updateDevInfo(DeveloperUpdateRequest developerUpdateRequest, String token) {
        String username = JWTService.extractUsername(token.split(" ")[1]);
        Optional<UserDomain> user = userRepository.findById(username);
        if (user.isEmpty()) {
            return Optional.of("could not update dev account");
        }
        Optional<DeveloperDomain> developer = developerRepository.findByUser(userRepository.findById(username).get());
        if (developer.isEmpty()) {
            return Optional.of("could not retrieve you're dev account");
        }
        DeveloperDomain dev = developer.get();
        dev.setPassword(developerUpdateRequest.getPassword());
        dev.setValid_urls(List.of(developerUpdateRequest.getValid_urls()));
        developerRepository.save(developer.get());
        return Optional.empty();
    }

    public Optional<String> verifyUrls(String client_ID, String url, String token) {
        String username = JWTService.extractUsername(token.split(" ")[1]);
        Optional<UserDomain> user = userRepository.findById(username);
        if (user.isEmpty()) {
            return Optional.of("could not retrieve user account");
        }
        UUID clientId = UUID.fromString(client_ID);
        Optional<DeveloperDomain> developer = developerRepository.findById(clientId);
        if (developer.isEmpty()) {
            return Optional.of("could not retrieve client information");
        }

        DeveloperDomain dev = developer.get();
        boolean containsUrl = dev.getValid_urls().contains(url);
        if (!containsUrl) {
            return Optional.of("the redirect url is invalid");
        }

        return Optional.empty();
    }

}
