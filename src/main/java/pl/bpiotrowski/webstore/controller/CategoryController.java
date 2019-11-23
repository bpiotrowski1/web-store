package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.dto.CategoryDto;
import pl.bpiotrowski.webstore.service.CategoryService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String addCategory(Model model) {
        model.addAttribute("categoryForm");
        return "admin/category";
    }

    @PostMapping
    public String createCategory(@Valid @ModelAttribute CategoryDto categoryForm) {
        categoryService.create(categoryForm);
        return "redirect:/admin/category";
    }

}
