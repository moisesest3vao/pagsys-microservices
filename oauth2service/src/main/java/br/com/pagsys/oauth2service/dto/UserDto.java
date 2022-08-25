package br.com.pagsys.oauth2service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {
    @NotNull
    @Size(max = 15, min = 4)
    private String userName;
    @NotNull
    @Size(max = 50, min = 4)
    private String email;
    @NotNull
    @Size(max = 15, min = 8)
    private String password;
    @NotNull
    @Size(max =20 , min = 8)
    private String mobile;

}
