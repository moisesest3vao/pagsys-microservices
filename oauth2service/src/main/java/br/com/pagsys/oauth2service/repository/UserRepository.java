package br.com.pagsys.oauth2service.repository;

import br.com.pagsys.oauth2service.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUserName(String username);

    Optional<AuthUser> findByUserNameOrEmail(String username, String email);
}
