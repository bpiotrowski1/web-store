package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.service.OrderService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    @GetMapping
    public String showOrders(Model model) {
        model.addAttribute("orderHeadersList", orderService.findAll());
        return "orders";
    }

}
