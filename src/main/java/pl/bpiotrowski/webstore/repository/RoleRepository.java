package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bpiotrowski.webstore.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
