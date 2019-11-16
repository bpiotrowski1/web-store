package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.service.CartService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String showShoppingCart(Model model) {
        model.addAttribute("shoppingCart", cartService.getCart());
        return "cart";
    }

}
