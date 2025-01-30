package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByStatusAndIdNotIn(Status status, List<Long> ids);
    Product findByStatusAndId(Status status, Long id);
    List<Product> findAllByStatus(Status status);
}
