package br.com.pagsys.msusers.service;

import br.com.pagsys.msusers.dto.GetUserByTokenResponse;
import br.com.pagsys.msusers.dto.User;
import br.com.pagsys.msusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserService {
    @Autowired
    private KeycloakUserService keycloakUserService;
    @Autowired
    private UserRepository userRepository;


    public User createUser(User user) {

        boolean isEmailAvailable = !(keycloakUserService.readUserByEmail(user.getEmail()).size() > 0);
        if (!isEmailAvailable) {
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

        if (userCreationResponse != 201) {
            throw new RuntimeException("We couldn't find user under given identification. Please check and retry");
        }

        log.info("User created under given username {}", user.getEmail());

        UserRepresentation savedUser = keycloakUserService.readUserByEmail(user.getEmail()).get(0);
        user.setAuthId(savedUser.getId());

        return user;


    }

    public Integer deleteUser(String email, String token) {
        List<UserRepresentation> queryResultByEmail = keycloakUserService.readUserByEmail(email);
        if(queryResultByEmail.isEmpty()){
           return 1;
        }
        UserRepresentation userToBeDeleted = queryResultByEmail.get(0);

        GetUserByTokenResponse userWhoRequestedDeletion = keycloakUserService.readUserByToken(token);

        if(Objects.equals(userWhoRequestedDeletion.getPreferred_username(), userToBeDeleted.getUsername())){
            return keycloakUserService.deleteUserByEmail(userToBeDeleted.getUsername());
        }

        return 1;
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
