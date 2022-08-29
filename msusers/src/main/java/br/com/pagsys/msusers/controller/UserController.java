package br.com.pagsys.msusers.controller;

import br.com.pagsys.msusers.dto.User;
import br.com.pagsys.msusers.service.KeycloakUserService;
import br.com.pagsys.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final KeycloakUserService keycloakUserService;
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity createUser(@RequestBody User request) {
        log.info("Creating user with {}", request.toString());
        return ResponseEntity.ok(userService.createUser(request));
    }

//    @GetMapping
//    public ResponseEntity readUsers(Pageable pageable) {
//        log.info("Reading all users from API");
//        return ResponseEntity.ok(userService.readUsers(pageable));
//    }
//
//    @GetMapping(value = "/{id}")
//    public ResponseEntity readUser(@PathVariable("id") Long id) {
//        log.info("Reading user by id {}", id);
//        return ResponseEntity.ok(userService.readUser(id));
//    }

}
