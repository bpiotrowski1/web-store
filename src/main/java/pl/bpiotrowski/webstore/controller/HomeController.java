package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.service.ProductsService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {

    private final ProductsService productsService;

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("topViewedProducts", productsService.findTopByViews());
        return "home";
    }

}
