package pl.bpiotrowski.webstore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FooterDto {

    private Long id;
    private String facebook;
    private String instagram;
    private String twitter;

}
