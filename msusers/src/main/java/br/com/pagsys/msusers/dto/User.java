package br.com.pagsys.msusers.dto;


import br.com.pagsys.msusers.model.UserEntity;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String identification;
    private String password;
    private String authId;

    public User(Long id, String email, String firstName, String lastName, String identification, String password, String authId) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identification = identification;
        this.password = password;
        this.authId = authId;
    }

    public UserEntity toEntity() {
        return new UserEntity(authId, email, firstName, lastName);
    }
}