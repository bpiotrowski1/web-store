package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bpiotrowski.webstore.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
