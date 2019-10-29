package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bpiotrowski.webstore.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r.title from Role r")
    List<String> findAllTitles();

    Role findByTitle(String title);

}
