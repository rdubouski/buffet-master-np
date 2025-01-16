package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.entity.Ingredient;
import by.tms.buffetmasternp.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllIngredients() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Tomato");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Lettuce");

        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient1, ingredient2));

        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        assertEquals(2, ingredients.size());
        assertEquals("Tomato", ingredients.get(0).getName());
    }

    @Test
    public void testCreateIngredient() {
        String ingredientName = "Cheese";
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);

        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient createdIngredient = ingredientService.createIngredient(ingredientName);

        assertNotNull(createdIngredient);
        assertEquals("Cheese", createdIngredient.getName());

        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository, times(1)).save(ingredientCaptor.capture());
        assertEquals("Cheese", ingredientCaptor.getValue().getName());
    }

    @Test
    public void testGetIngredientById() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Salt");
        Long ingredientId = 1L;

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        Ingredient foundIngredient = ingredientService.getIngredientById(ingredientId);

        assertNotNull(foundIngredient);
        assertEquals("Salt", foundIngredient.getName());
    }

    @Test
    public void testGetIngredientByIdNotFound() {
        Long ingredientId = 1L;

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        Ingredient foundIngredient = ingredientService.getIngredientById(ingredientId);

        assertNull(foundIngredient);
    }

    @Test
    public void testUpdateIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Pepper");

        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient updatedIngredient = ingredientService.updateIngredient(ingredient);

        assertNotNull(updatedIngredient);
        assertEquals("Pepper", updatedIngredient.getName());

        verify(ingredientRepository, times(1)).save(ingredient);
    }
}