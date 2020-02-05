package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bpiotrowski.webstore.service.ProductsService;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class ProductsController {

    private final ProductsService productService;

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("productList", productService.findAll(true)); // ALWAYS give active products to users
        return "products";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(name = "q") String query, Model model) {
        model.addAttribute("productList", productService.findAllByQuery(query));
        return "products";
    }

    @GetMapping("/admin/products")
    public String getAdminProducts(@RequestParam(name = "active", required = false) Boolean active, Model model) {
        model.addAttribute("productList", productService.findAll(active));
        return "admin/products";
    }

    @GetMapping("/admin/products/search")
    public String searchAdminProducts(@RequestParam(name = "q") String query, Model model) {
        model.addAttribute("productList", productService.findAllByQuery(query));
        return "admin/products";
    }

}
