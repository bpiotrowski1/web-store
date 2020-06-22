package pl.bpiotrowski.webstore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AboutDto {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String content;

}
