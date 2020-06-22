package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.service.AddressService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final AddressService addressService;

    @GetMapping
    public String getPayment(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("addressForm", addressService.findAddressByUserId(user.getId()));
        return "payment";
    }

}
