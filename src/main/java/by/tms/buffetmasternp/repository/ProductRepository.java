package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
