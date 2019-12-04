package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bpiotrowski.webstore.service.OrderService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/orders")
public class OrdersController {

    private final OrderService orderService;

    @GetMapping
    public String showOrders(Model model, @RequestParam(required = false, name = "done") String done, @RequestParam(name = "p") int p) {
        model.addAttribute("orderHeadersList", orderService.findAll(p - 1, done));
        model.addAttribute("pageNumbers", orderService.getOrdersTotalPages());
        return "admin/orders";
    }

    @GetMapping("/done/{id}")
    public String makeDone(@PathVariable Long id) {
        orderService.makeDone(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/undo/{id}")
    public String undo(@PathVariable Long id) {
        orderService.undo(id);
        return "redirect:/admin/orders";
    }

}
