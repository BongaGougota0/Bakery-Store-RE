package za.co.bakery.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.bakery.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<Long, HistoryItem> {
}
