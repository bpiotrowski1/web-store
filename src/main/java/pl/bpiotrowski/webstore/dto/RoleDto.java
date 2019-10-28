package pl.bpiotrowski.webstore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RoleDto {

    @NotNull
    @Size(min = 4, max = 255)
    private String title;

}
