package io.exsuslabs.AuthorizationServer.repository;

import io.exsuslabs.AuthorizationServer.domain.DeveloperDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeveloperRepository extends CrudRepository<DeveloperDomain,Long> {
    Optional<DeveloperDomain> findByUserId(Long user_id);
    Optional<DeveloperDomain> findById(UUID id);

}
