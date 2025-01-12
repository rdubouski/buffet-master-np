package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
