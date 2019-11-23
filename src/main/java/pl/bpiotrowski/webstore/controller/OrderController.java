package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.service.AddressService;
import pl.bpiotrowski.webstore.service.OrderService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final AddressService addressService;

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        model.addAttribute("itemList", orderService.getItems(id));
        model.addAttribute("orderHeader", orderService.getHeader(id));
        model.addAttribute("address", addressService.findAddressByOrderId(id));
        return "order";
    }

    @PostMapping
    public String placeOrder(@AuthenticationPrincipal User user) {
        orderService.placeOrder(user.getId());
        return "redirect:/";
    }

}
