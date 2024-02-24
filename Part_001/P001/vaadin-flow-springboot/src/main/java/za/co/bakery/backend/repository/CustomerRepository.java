package za.co.bakery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.bakery.backend.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Long, Customer> {
}
