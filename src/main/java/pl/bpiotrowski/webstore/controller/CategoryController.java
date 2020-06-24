package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bpiotrowski.webstore.dto.CategoryDto;
import pl.bpiotrowski.webstore.service.CategoryService;
import pl.bpiotrowski.webstore.service.ProductsService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductsService productsService;

    @GetMapping("/admin/category")
    public String addCategory(Model model) {
        model.addAttribute("categoryForm");
        return "admin/category";
    }

    @GetMapping("/category/{id}")
    public String showCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("productList", productsService.findAllByCategoryId(id));
        return "category";
    }

    @PostMapping("admin/category")
    public String createCategory(@Valid @ModelAttribute CategoryDto categoryForm) {
        categoryService.create(categoryForm);
        return "redirect:/admin/category?category_added";
    }

}
