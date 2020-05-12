package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.dto.AddressDto;

@RequiredArgsConstructor
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping
    public String getPayment(Model model) {
        model.addAttribute("addressForm", new AddressDto());
        return "payment";
    }

}
