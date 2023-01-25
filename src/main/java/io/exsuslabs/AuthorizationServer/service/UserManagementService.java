package io.exsuslabs.AuthorizationServer.service;

import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator;
import com.google.common.hash.Hashing;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.repository.UserRepository;
import io.exsuslabs.AuthorizationServer.requests.FullUserRequest;
import io.exsuslabs.AuthorizationServer.utils.Convertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository repository;

    public Optional<String> createUser(FullUserRequest fullUserRequest) {
        Optional<UserDomain> byUsername = repository.findByUsername(fullUserRequest.getUsername());
        if (byUsername.isPresent()) {
            return Optional.of("User is already registered. Use a different username");
        }
        UserDomain user = new UserDomain();
        user.setEmail(fullUserRequest.getEmail());
        user.setUsername(fullUserRequest.getUsername());
        user.setPassword(Hashing.sha512().hashString(fullUserRequest.getPassword(), StandardCharsets.UTF_8).toString());
        repository.save(user);
        return Optional.empty();
    }

    public Map<String, String> getUserInfo(long id) {
        Optional<UserDomain> userDomain = repository.findById(id);
        return userDomain.map(Convertions::convertUserToMap).orElse(null);
    }


}
