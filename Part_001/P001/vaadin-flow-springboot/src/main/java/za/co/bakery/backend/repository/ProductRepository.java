package za.co.bakery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.bakery.backend.data.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
