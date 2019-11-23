package pl.bpiotrowski.webstore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddressDto {

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

}
