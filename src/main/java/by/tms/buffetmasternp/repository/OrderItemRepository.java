package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
