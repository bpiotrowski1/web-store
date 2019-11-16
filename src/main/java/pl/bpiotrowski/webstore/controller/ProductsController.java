package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.service.ProductsService;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class ProductsController {

    private final ProductsService productService;

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("productList", productService.findAll());
        return "products";
    }

    @GetMapping("/admin/products")
    public String getAdminProducts(Model model) {
        model.addAttribute("productList", productService.findAll());
        return "admin/products";
    }

}
