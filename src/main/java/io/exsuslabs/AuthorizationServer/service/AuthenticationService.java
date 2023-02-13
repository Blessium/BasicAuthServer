package io.exsuslabs.AuthorizationServer.service;

import com.google.common.hash.Hashing;
import io.exsuslabs.AuthorizationServer.domain.DeveloperDomain;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.jwt.JWTService;
import io.exsuslabs.AuthorizationServer.repository.DeveloperRepository;
import io.exsuslabs.AuthorizationServer.repository.UserRepository;
import io.exsuslabs.AuthorizationServer.requests.CredentialUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
@ComponentScan(basePackageClasses = DeveloperRepository.class)
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DeveloperRepository developerRepository;

    public Optional<String> validateCredentials(CredentialUserRequest credentialUserRequest) {
        Optional<UserDomain> user = userRepository.lookupCredentials(credentialUserRequest.getUsername(), Hashing.sha512().hashString(credentialUserRequest.getPassword(), StandardCharsets.UTF_8).toString());
        if (user.isEmpty()) {
            return Optional.empty();
        }

        String jwtToken = JWTService.generateJWT(user.get());
        return Optional.of(jwtToken);
    }

    public Optional<String> validateDeveloperCredentials(String clientId, String password) {
        Optional<DeveloperDomain> dev = developerRepository.lookupCredentials(UUID.fromString(clientId), password);
        if (dev.isEmpty()) {
            return Optional.of("invalid password or clientId");
        }
        return Optional.empty();
    }


}
