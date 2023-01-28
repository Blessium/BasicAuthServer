package io.exsuslabs.AuthorizationServer.repository;

import io.exsuslabs.AuthorizationServer.domain.DeveloperDomain;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeveloperRepository extends CrudRepository<DeveloperDomain, UUID> {
    Optional<DeveloperDomain> findByUser(UserDomain user);
}
