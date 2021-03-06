package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bpiotrowski.webstore.entity.Product;

import java.lang.annotation.Native;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryId(Long id);
    List<Product> findAllByTitleContains(String query);
    List<Product> findAllByActive(Boolean active);

    @Query(value = "SELECT * FROM Products p ORDER BY p.views DESC LIMIT 3", nativeQuery = true)
    List<Product> findTop3Viewed();

}
