package pl.bpiotrowski.webstore.dto;

import lombok.Data;
import pl.bpiotrowski.webstore.entity.Category;

import javax.validation.constraints.*;

@Data
public class ProductDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    @Size(min = 3)
    private String description;

    private String thumbnail;

    @NotNull
    private Category category;

    @NotNull
    @Positive
    private Double price;
    //    private Type type;

    private Boolean active;

    private Long views;

    @NotNull
    @Positive
    private Integer quantity;

//    @NotNull
//    private User author;

}
