package br.com.pagsys.msusers.model;

import br.com.pagsys.msusers.dto.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sub;
    private String email;
    private String givenName;
    private String familyName;

    public UserEntity(String sub, String email, String firstName, String lastName) {
        this.sub = sub;
        this.email = email;
        this.givenName = firstName;
        this.familyName = lastName;
    }

    public User toDto() {
        return new User(id,email,givenName,familyName,"","",sub);
    }
}
