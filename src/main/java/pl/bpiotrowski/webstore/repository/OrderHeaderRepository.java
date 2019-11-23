package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bpiotrowski.webstore.entity.OrderHeader;

import java.util.List;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {

    @Query("select max(id) from OrderHeader o")
    Long findMaxId();

    @Query("select o from OrderHeader o where user_id=:id")
    List<OrderHeader> findAllByUserId(Long id);

    List<OrderHeader> findAllByDone(boolean done);

}
