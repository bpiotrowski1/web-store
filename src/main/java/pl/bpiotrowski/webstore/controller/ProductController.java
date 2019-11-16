package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.service.CartService;
import pl.bpiotrowski.webstore.service.CategoryService;
import pl.bpiotrowski.webstore.service.ProductService;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping
    public String addProduct(Model model) {
        model.addAttribute("productForm", new ProductDto());
        model.addAttribute("categoryList", categoryService.findAll());
        return "product";
    }

    @GetMapping("/buy/{id}")
    public String addProductToCart(@PathVariable Long id, @RequestParam(name = "quantity", required = false) Integer quantity) {
        cartService.addProductToCart(quantity, id);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeProductFromCart(@PathVariable Long id) {
        cartService.removeProductFromCart(id);
        return "redirect:/cart";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute ProductDto productDto, Principal principal) {
        productService.create(productDto, principal.getName());
        return "redirect:/product";
    }
}
