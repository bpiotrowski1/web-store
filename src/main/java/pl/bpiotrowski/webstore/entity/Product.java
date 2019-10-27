package pl.bpiotrowski.webstore.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String thumbnail;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "category_id")
//    private Category category;

    private Double price;

//    Enumerated
//    private Type type;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "user_id")
//    private User user;

}
