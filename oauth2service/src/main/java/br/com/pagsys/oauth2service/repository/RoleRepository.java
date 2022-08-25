package br.com.pagsys.oauth2service.repository;

import br.com.pagsys.oauth2service.model.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<AuthRole, Long> {
    AuthRole findByRoleNameContaining(String roleName);
}
