package pl.bpiotrowski.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.bpiotrowski.webstore.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select a from Address a where user_id=:id")
    Address findByUserId(@Param("id") Long id);
}
