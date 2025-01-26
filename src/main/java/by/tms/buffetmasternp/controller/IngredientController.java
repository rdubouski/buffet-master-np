package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.entity.Ingredient;
import by.tms.buffetmasternp.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/all")
    public String getIngredients(Model model) {
        model.addAttribute("ingredients", ingredientService.getAllIngredients());
        return "ingredient/all";
    }

    @PostMapping("/add")
    public String addIngredient(@ModelAttribute("ingredientName") String ingredientName, Model model) {
        ingredientService.createIngredient(ingredientName);
        model.addAttribute("ingredients", ingredientService.getAllIngredients());
        return "redirect:/admin/ingredient/all";
    }

    @GetMapping("/edit/{id}")
    public String editIngredient(@PathVariable("id") Long id, Model model) {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        model.addAttribute("ingredient", ingredient);
        return "ingredient/edit";
    }

    @PostMapping("/edit")
    public String editIngredient(@ModelAttribute("ingredient") Ingredient ingredient, Model model) {
        ingredientService.updateIngredient(ingredient);
        model.addAttribute("ingredients", ingredientService.getAllIngredients());
        return "redirect:/admin/ingredient/all";
    }

}
