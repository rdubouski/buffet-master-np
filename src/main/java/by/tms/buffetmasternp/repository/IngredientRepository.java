package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
