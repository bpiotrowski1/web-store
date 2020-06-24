package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bpiotrowski.webstore.dto.AddressDto;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.exception.QuantityBelowZeroException;
import pl.bpiotrowski.webstore.service.AddressService;
import pl.bpiotrowski.webstore.service.OrderService;

import javax.validation.Valid;

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
    public String placeOrder(@Valid @ModelAttribute AddressDto addressDto, @AuthenticationPrincipal User user) throws QuantityBelowZeroException {
        orderService.placeOrder(user.getId(), addressDto);
        return "redirect:/?order_success";
    }

}
