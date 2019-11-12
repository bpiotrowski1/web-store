package pl.bpiotrowski.webstore.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_header_id")
    private OrderHeader orderHeader;

}
