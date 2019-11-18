package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bpiotrowski.webstore.entity.Footer;

public interface FooterRepository extends JpaRepository<Footer, Long> {

    @Query("select count(f) from Footer f")
    int findCount();

}
