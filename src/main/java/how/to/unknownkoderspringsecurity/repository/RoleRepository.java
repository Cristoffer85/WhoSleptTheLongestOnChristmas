package how.to.unknownkoderspringsecurity.repository;

import how.to.unknownkoderspringsecurity.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Integer> {
    Optional<Role> findByAuthority(String authority);
}