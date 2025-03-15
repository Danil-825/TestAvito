package com.example.TestAvito.controller;


import com.example.TestAvito.entity.Product;
import com.example.TestAvito.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String products(@RequestParam(name = "name", required = false) String name, Model model) {
        model.addAttribute("products", productService.listProducts(name));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/"; // Если товар не найден, перенаправляем на главную
        }
        model.addAttribute("product", product); // Передаем один товар в шаблон
        return "product-info";
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
