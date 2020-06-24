package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.service.CategoryService;
import pl.bpiotrowski.webstore.service.ProductService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/product/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        productService.increaseViews(id);
        model.addAttribute("product", productService.getOne(id));
        return "product";
    }

    @GetMapping("/admin/product/{id}/{quantity}")
    public String changeQuantity(@PathVariable Long id, @PathVariable Integer quantity, @AuthenticationPrincipal User user) {
        productService.changeQuantity(id, quantity, user);
        return "admin/products";
    }

    @GetMapping("/admin/product/add")
    public String addProduct(Model model) {
        model.addAttribute("productForm", new ProductDto());
        model.addAttribute("categoryList", categoryService.findAll());
        return "admin/addProduct";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable Long id) {
        model.addAttribute("productForm", productService.getOne(id));
        model.addAttribute("categoryList", categoryService.findAll());
        return "admin/editProduct";
    }

    @GetMapping("/admin/product/hide/{id}")
    public String hideProduct(@PathVariable Long id) {
        productService.hide(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/show/{id}")
    public String showProduct(@PathVariable Long id) {
        productService.show(id);
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@Valid @ModelAttribute ProductDto productDto, @AuthenticationPrincipal User user) {
        Long id = productService.create(productDto, user.getId());
        return "redirect:/admin/thumbnail?id=" + id;
    }

    @PostMapping("/admin/product/edit/{id}")
    public String editProduct(@Valid @ModelAttribute ProductDto productDto, @PathVariable Long id) {
        productDto.setId(id);
        productService.update(productDto);
        return "redirect:/admin/products";
    }

}
