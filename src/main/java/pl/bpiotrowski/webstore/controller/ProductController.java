package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.service.CategoryService;
import pl.bpiotrowski.webstore.service.ProductService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getOne(id));
        return "product";
    }

    @GetMapping("/{id}/{quantity}")
    public String changeQuantity(@PathVariable Long id, @PathVariable Integer quantity) {
        productService.changeQuantity(id, quantity);
        return "/admin/products";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("productForm", new ProductDto());
        model.addAttribute("categoryList", categoryService.findAll());
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductDto productDto, @AuthenticationPrincipal User user) {
        productService.create(productDto, user.getId());
        return "redirect:/products";
    }
}
