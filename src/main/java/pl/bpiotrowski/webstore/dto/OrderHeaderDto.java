package pl.bpiotrowski.webstore.dto;

import lombok.Builder;
import lombok.Data;
import pl.bpiotrowski.webstore.entity.Address;
import pl.bpiotrowski.webstore.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class OrderHeaderDto {

    private Long id;

    @NotBlank
    @Size(min = 6)
    private String number;

    @NotNull
    private User purchaser;

    @NotNull
    private String date;

    @NotNull
    private boolean done;

    @NotNull
    private Address address;

}
