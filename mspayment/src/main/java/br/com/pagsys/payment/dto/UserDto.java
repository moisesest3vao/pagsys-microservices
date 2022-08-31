package br.com.pagsys.payment.dto;

import br.com.pagsys.payment.model.AuthUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String sub;
    private Boolean email_verified;
    private String name;
    private String preferred_username;
    private String given_name;
    private String family_name;
    private String email;

    public UserDto (AuthUser user) {
        sub = user.getSub();
        email_verified = user.getEmail_verified();
        name = user.getName();
        preferred_username = user.getPreferred_username();
        given_name = user.getGiven_name();
        family_name = user.getFamily_name();
        email = user.getEmail();
    }
}
