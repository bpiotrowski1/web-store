package pl.bpiotrowski.webstore.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "footer")
public class Footer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String facebook;
    private String instagram;
    private String twitter;

}
