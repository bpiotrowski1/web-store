package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.service.OrderService;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String placeOrder(HttpSession session, Principal principal) {
        orderService.placeOrder(session, principal.getName());
        return "order";
    }
}
