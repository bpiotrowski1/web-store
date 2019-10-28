package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.service.ProductsService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productService;

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("productList", productService.findAll());
        return "products";
    }

}
