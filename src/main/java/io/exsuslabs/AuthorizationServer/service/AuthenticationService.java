package io.exsuslabs.AuthorizationServer.service;

import com.google.common.hash.Hashing;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.jwt.JWTService;
import io.exsuslabs.AuthorizationServer.repository.UserRepository;
import io.exsuslabs.AuthorizationServer.requests.CredentialUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    public Optional<String> validateCredentials(CredentialUserRequest credentialUserRequest) {
        Optional<UserDomain> user = userRepository.lookupCredentials(credentialUserRequest.getUsername(), Hashing.sha512().hashString(credentialUserRequest.getPassword(), StandardCharsets.UTF_8).toString());
        if (user.isEmpty()) {
            return Optional.empty();
        }

        String jwtToken = JWTService.generateJWT(user.get());
        return Optional.of(jwtToken);
    }

}
