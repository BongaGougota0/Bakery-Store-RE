package za.co.bakery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.bakery.backend.data.entity.Product;

public interface ProductRepository extends JpaRepository<Long, Product> {
}
