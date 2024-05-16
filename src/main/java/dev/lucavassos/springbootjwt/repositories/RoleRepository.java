package dev.lucavassos.springbootjwt.repositories;

import dev.lucavassos.springbootjwt.entities.Role;
import dev.lucavassos.springbootjwt.entities.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
