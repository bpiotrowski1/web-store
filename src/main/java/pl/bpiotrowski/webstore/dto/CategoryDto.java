package pl.bpiotrowski.webstore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

}
