package br.com.pagsys.payment.model;

import br.com.pagsys.payment.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sub;
    private Boolean email_verified;
    private String name;
    private String preferred_username;
    private String given_name;
    private String family_name;
    private String email;


    public AuthUser(UserDto userByToken) {
        sub = userByToken.getSub();
        email_verified = userByToken.getEmail_verified();
        name = userByToken.getName();
        preferred_username = userByToken.getPreferred_username();
        given_name = userByToken.getGiven_name();
        family_name = userByToken.getFamily_name();
        email = userByToken.getEmail();
    }

    public static AuthUser build(UserDto userByToken) {
        return new AuthUser(userByToken);
    }
}