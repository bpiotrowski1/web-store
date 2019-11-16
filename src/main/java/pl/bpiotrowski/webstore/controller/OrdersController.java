package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/done/{id}")
    public String makeDone(@PathVariable Long id) {
        orderService.makeDone(id);
        return "redirect:/orders";
    }

    @GetMapping("/undo/{id}")
    public String undo(@PathVariable Long id) {
        orderService.undo(id);
        return "redirect:/orders";
    }

}