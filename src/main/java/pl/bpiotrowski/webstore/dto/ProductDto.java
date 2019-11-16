package pl.bpiotrowski.webstore.dto;

import lombok.Data;
import pl.bpiotrowski.webstore.entity.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class ProductDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    private String description;

    @NotBlank
    @Size(max = 255)
    private String thumbnail;

    @NotNull
    private Category category;

    @Positive
    private Double price;
    //    private Type type;

    @NotNull
    private Integer quantity;

//    @NotNull
//    private User author;

}
