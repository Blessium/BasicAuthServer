package io.exsuslabs.AuthorizationServer.repository;

import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDomain, Long> {
    Optional<UserDomain> findByEmail(String email);
    Optional<UserDomain> findByUsername(String username);
    @Query(value = "SELECT * from users WHERE username=:username AND password=:password", nativeQuery = true)
    Optional<UserDomain> lookupCredentials(@Param("username") String username, @Param("password") String hashedPassword);
}
