package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.service.IngredientService;
import by.tms.buffetmasternp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final IngredientService ingredientService;

    public ProductController(ProductService productService, IngredientService ingredientService) {
        this.productService = productService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/admin/all")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAllProductsByStatus(Status.OPEN));
        return "product/all";
    }

    @GetMapping("/admin/archive")
    public String allArchiveProducts(Model model) {
        model.addAttribute("products", productService.getAllProductsByStatus(Status.CLOSED));
        return "product/all";
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
        return "redirect:/product/admin/all";
    }

    @PostMapping("/admin/close")
    public String closeProduct(@RequestParam("id") Long id) {
        productService.closeProduct(id);
        return "redirect:/product/admin/all";
    }

    @GetMapping("/admin/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/edit";
    }

    @PostMapping("/admin/edit")
    public String editProduct(@RequestParam("id") Long id) {
        System.out.println("editProduct" + id);
        return "redirect:/product/admin/all";
    }
}
