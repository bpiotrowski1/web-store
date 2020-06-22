package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bpiotrowski.webstore.entity.About;

public interface AboutRepository extends JpaRepository<About, Long> {
}
