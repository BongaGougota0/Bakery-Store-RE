package za.co.bakery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.bakery.backend.data.entity.HistoryItem;
@Repository
public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
