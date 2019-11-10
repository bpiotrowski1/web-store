package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bpiotrowski.webstore.entity.OrderHeader;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
