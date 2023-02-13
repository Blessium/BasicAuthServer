package io.exsuslabs.AuthorizationServer.repository;

import io.exsuslabs.AuthorizationServer.domain.DeveloperDomain;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeveloperRepository extends CrudRepository<DeveloperDomain, UUID> {
    Optional<DeveloperDomain> findByUser(UserDomain user);


    @Query(value = "SELECT * from developers WHERE id=:id AND password=:password", nativeQuery = true)
    Optional<DeveloperDomain> lookupCredentials(@Param("id") UUID id, @Param("password") String password);
}
