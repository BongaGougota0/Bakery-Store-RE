package za.co.bakery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.bakery.backend.data.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
