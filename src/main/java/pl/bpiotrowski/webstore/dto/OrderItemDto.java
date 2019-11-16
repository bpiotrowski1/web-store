package pl.bpiotrowski.webstore.dto;

import lombok.Builder;
import lombok.Data;
import pl.bpiotrowski.webstore.entity.OrderHeader;
import pl.bpiotrowski.webstore.entity.Product;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class OrderItemDto {

    private Long id;

    @NotBlank
    private Integer quantity;

    @NotBlank
    private Product item;

    @NotBlank
    private OrderHeader orderHeader;

}
