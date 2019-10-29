package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bpiotrowski.webstore.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByTitle(String title);

}
