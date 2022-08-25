package br.com.pagsys.oauth2service.controller;

import br.com.pagsys.oauth2service.dto.UserDto;
import br.com.pagsys.oauth2service.model.AuthUser;
import br.com.pagsys.oauth2service.repository.RoleRepository;
import br.com.pagsys.oauth2service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/oauth/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto){
        Boolean isUserNameOrEmailTaken = userRepository
                .findByUserNameOrEmail(userDto.getUserName(), userDto.getEmail()).isPresent();
        if(isUserNameOrEmailTaken){
            return ResponseEntity.badRequest().body("Username and/or email already taken");
        }

        AuthUser authUser = new ObjectMapper().convertValue(userDto, AuthUser.class);
        authUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        authUser.setRoles(Collections.singletonList(roleRepository.findByRoleNameContaining("USER")));


        AuthUser save = userRepository.save(authUser);
        return ResponseEntity.status(201).body(save);
    }

}
