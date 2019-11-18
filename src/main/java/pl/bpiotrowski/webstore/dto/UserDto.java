package pl.bpiotrowski.webstore.dto;

import lombok.Data;
import pl.bpiotrowski.webstore.entity.Role;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotBlank
    @Size(min = 4, max = 255)
    private String username;

    @NotBlank
//    @Size(min = 6, max = 255)
    private String password;

    @NotBlank
//    @Size(min = 6, max = 255)
    private String repeatPassword;

    @Email
    @Size(min = 6, max = 255)
    private String email;

    @NotBlank
    @Size(min = 2, max = 255)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 255)
    private String lastName;

    @Size(min = 2, max = 255)
    private String street;

    @NotBlank
    @Size(min = 1, max = 255)
    private String houseNumber;

    private Role role;

}
