package pl.bpiotrowski.webstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.bpiotrowski.webstore.entity.OrderHeader;

import java.util.List;

public interface OrderHeaderRepository extends PagingAndSortingRepository<OrderHeader, Long> {

    @Query("select max(id) from OrderHeader o")
    Long findMaxId();

    @Query("select o from OrderHeader o where user_id=:id")
    Page<OrderHeader> findAllByUserId(Pageable pageable, Long id);

    @Query("select count(id) from OrderHeader o where address_id=:id")
    Long findCountByAddressId(Long id);

    Page<OrderHeader> findAllByDone(Pageable pageable, boolean done);

}
