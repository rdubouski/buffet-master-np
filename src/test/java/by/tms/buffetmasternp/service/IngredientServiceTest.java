package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.entity.Ingredient;
import by.tms.buffetmasternp.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIngredients() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Tomato");

        when(ingredientRepository.findAll()).thenReturn(Collections.singletonList(ingredient));

        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
        assertEquals("Tomato", ingredients.getFirst().getName());
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    void testCreateIngredient() {
        String ingredientName = "Lettuce";
        ingredientService.createIngredient(ingredientName);

        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);

        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void testGetIngredientById() {
        Long id = 1L;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName("Cucumber");

        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));

        Ingredient foundIngredient = ingredientService.getIngredientById(id);

        assertNotNull(foundIngredient);
        assertEquals("Cucumber", foundIngredient.getName());
        verify(ingredientRepository, times(1)).findById(id);
    }

    @Test
    void testGetIngredientById_NotFound() {
        Long id = 2L;

        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());

        Ingredient foundIngredient = ingredientService.getIngredientById(id);

        assertNull(foundIngredient);
        verify(ingredientRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Onion");

        ingredientService.updateIngredient(ingredient);

        verify(ingredientRepository, times(1)).save(ingredient);
    }
}