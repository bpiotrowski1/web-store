package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.service.CategoryService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("categoriesList", categoryService.findAll());
        return "categories";
    }

}
