package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bpiotrowski.webstore.entity.About;

public interface AboutRepository extends JpaRepository<About, Long> {

    @Query("select count(a) from About a")
    int findCount();

}
