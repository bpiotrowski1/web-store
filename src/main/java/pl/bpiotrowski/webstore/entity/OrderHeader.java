package pl.bpiotrowski.webstore.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders_header")
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private boolean done;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User purchaser;

}
