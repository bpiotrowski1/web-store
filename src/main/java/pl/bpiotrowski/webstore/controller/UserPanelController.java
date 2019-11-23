package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.dto.AddressDto;
import pl.bpiotrowski.webstore.service.AddressService;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user-panel")
public class UserPanelController {

    private final AddressService addressService;

    @GetMapping
    public String getUserPanel(Model model, Principal principal) {
        model.addAttribute("addressForm", addressService.findAddress(principal.getName()));
        return "userPanel";
    }

    @PostMapping
    public String updateAddress(@Valid @ModelAttribute AddressDto addressDto, Principal principal) {
        addressService.create(addressDto, principal.getName());
        return "redirect:/user-panel";
    }

}
