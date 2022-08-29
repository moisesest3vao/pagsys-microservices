package br.com.pagsys.msusers.service;

import br.com.pagsys.msusers.dto.User;
import br.com.pagsys.msusers.model.UserEntity;
import br.com.pagsys.msusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakUserService keycloakUserService;
    private final UserRepository userRepository;

    public User createUser(User user) {

        List<UserRepresentation> userRepresentations = keycloakUserService.readUserByEmail(user.getEmail());
        if (userRepresentations.size() > 0) {
            throw new RuntimeException("This email already registered as a user. Please check and retry.");
        }

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        Integer userCreationResponse = keycloakUserService.createUser(userRepresentation);

        if (userCreationResponse == 201) {
            log.info("User created under given username {}", user.getEmail());

            List<UserRepresentation> userRepresentations1 = keycloakUserService.readUserByEmail(user.getEmail());
            user.setAuthId(userRepresentations1.get(0).getId());

            return user;
        }

        throw new RuntimeException("We couldn't find user under given identification. Please check and retry");

    }

//    public List<User> readUsers(Pageable pageable) {
//        Page<UserEntity> allUsersInDb = userRepository.findAll(pageable);
//        List<User> users = userMapper.convertToDtoList(allUsersInDb.getContent());
//        users.forEach(user -> {
//            UserRepresentation userRepresentation = keycloakUserService.readUser(user.getAuthId());
//            user.setId(user.getId());
//            user.setEmail(userRepresentation.getEmail());
//            user.setIdentification(user.getIdentification());
//        });
//        return users;
//    }
//
//    public User readUser(Long userId) {
//        return userMapper.convertToDto(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
//    }
//
//    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
//        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//
//        if (userUpdateRequest.getStatus() == Status.APPROVED) {
//            UserRepresentation userRepresentation = keycloakUserService.readUser(userEntity.getAuthId());
//            userRepresentation.setEnabled(true);
//            userRepresentation.setEmailVerified(true);
//            keycloakUserService.updateUser(userRepresentation);
//        }
//
//        userEntity.setStatus(userUpdateRequest.getStatus());
//        return userMapper.convertToDto(userRepository.save(userEntity));
//    }

}