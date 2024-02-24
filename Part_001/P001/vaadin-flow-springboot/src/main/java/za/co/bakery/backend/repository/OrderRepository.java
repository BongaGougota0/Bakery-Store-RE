package za.co.bakery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.bakery.backend.data.entity.Order;

public interface OrderRepository extends JpaRepository<Long, Order> {
}
