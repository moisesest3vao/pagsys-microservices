package br.com.pagsys.msusers.controller;

import br.com.pagsys.msusers.dto.GetUserByTokenResponse;
import br.com.pagsys.msusers.dto.User;
import br.com.pagsys.msusers.enums.EmailType;
import br.com.pagsys.msusers.service.KeycloakUserService;
import br.com.pagsys.msusers.service.UserService;
import br.com.pagsys.msusers.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private KeycloakUserService keycloakUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody User request) {
        log.info("Creating user with {}", request.toString());

        User user = userService.createUser(request);

        log.info("Sending message to email microsservice");
        kafkaTemplate.send("USER-LIFECYCLE-EVENTS",user.getEmail(), EmailType.JOINER.toString());

        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/delete/{email}")
    public ResponseEntity<?> createUser(@PathVariable String email, HttpServletRequest request) {
        log.info("Creating user with {}", request.toString());
        String token = request.getHeader("authorization");
        Integer response = userService.deleteUser(email, token);
        if(response == 0){
            log.info("Sending message to email microsservice");
            kafkaTemplate.send("USER-LIFECYCLE-EVENTS",email, EmailType.LEAVER.toString());

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getcurrent")
    public ResponseEntity<GetUserByTokenResponse> getUserByToken(HttpServletRequest request){
        String authorization = request.getHeader("authorization");
        if(StringUtil.isTokenFormatValid(authorization)){
            GetUserByTokenResponse getUserByTokenResponse =
                    this.keycloakUserService.readUserByToken(authorization);
            return getUserByTokenResponse
                    != null ? ResponseEntity.ok(getUserByTokenResponse) : ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build();
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
