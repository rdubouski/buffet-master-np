package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.BoxItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxItemRepository extends JpaRepository<BoxItem, Integer> {
    List<BoxItem> findByBoxId(Long boxId);
}
