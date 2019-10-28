package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.service.ProductService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String addProduct(Model model) {
        model.addAttribute("productForm", new ProductDto());
        return "product";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute ProductDto productDto) {
        productService.create(productDto);
        return "redirect:/product";
    }
}
