package za.co.bakery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.bakery.backend.data.entity.User;

public interface UserRepository extends JpaRepository<Long, User> {
}
