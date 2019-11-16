package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

}
