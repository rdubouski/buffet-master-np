package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIdNotIn(List<Long> ids);
}
