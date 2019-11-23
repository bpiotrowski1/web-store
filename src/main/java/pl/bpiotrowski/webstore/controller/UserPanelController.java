package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.dto.AddressDto;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.service.AddressService;
import pl.bpiotrowski.webstore.service.OrderService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user-panel")
public class UserPanelController {

    private final AddressService addressService;
    private final OrderService orderService;

    @GetMapping
    public String getUserPanel(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("addressForm", addressService.findAddress(user.getId()));
        model.addAttribute("ordersList", orderService.findAllByUserId(user.getId()));
        return "userPanel";
    }

    @PostMapping
    public String updateAddress(@Valid @ModelAttribute AddressDto addressDto, @AuthenticationPrincipal User user) {
        addressService.create(addressDto, user.getId());
        return "redirect:/user-panel";
    }

}
