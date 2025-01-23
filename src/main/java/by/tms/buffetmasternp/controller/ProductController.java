package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.service.IngredientService;
import by.tms.buffetmasternp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final IngredientService ingredientService;

    public ProductController(ProductService productService, IngredientService ingredientService) {
        this.productService = productService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/admin/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("ingredientsAll", ingredientService.getAllIngredients());
        return "product/add";
    }

    @PostMapping("/admin/add")
    public String addProduct(Product product) {
        productService.save(product);
        return "redirect:/product/admin/add";
    }
}
