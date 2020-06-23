package pl.bpiotrowski.webstore.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "about")
public class About {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

}
