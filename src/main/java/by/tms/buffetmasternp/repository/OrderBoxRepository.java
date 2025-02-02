package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.OrderBox;
import by.tms.buffetmasternp.enums.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderBoxRepository extends JpaRepository<OrderBox, Long> {
    List<OrderBox> findAllByStatusOrderByDateAsc(StatusOrder status);
    List<OrderBox> findAllByStatusAndAccountIdOrderByDateAsc(StatusOrder status, Long accountId);
}
