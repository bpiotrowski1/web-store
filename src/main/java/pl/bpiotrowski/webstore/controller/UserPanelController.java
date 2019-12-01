package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bpiotrowski.webstore.dto.AddressDto;
import pl.bpiotrowski.webstore.entity.OrderHeader;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.service.AddressService;
import pl.bpiotrowski.webstore.service.OrderService;
import pl.bpiotrowski.webstore.service.UserService;

import javax.validation.Valid;

import static pl.bpiotrowski.webstore.statics.Constants.USER_PANEL_PAGE_SIZE;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user-panel")
public class UserPanelController {

    private final AddressService addressService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String getUserPanel(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "p") int p) {
//        Page<OrderHeader> page = orderService.findAllByUserId(PageRequest.of(p - 1, USER_PANEL_PAGE_SIZE), user.getId());
//        model.addAttribute("addressForm", addressService.findAddress(user.getId()));
//        model.addAttribute("ordersList", page);
        model.addAttribute("pageNumbers", orderService.getTotalPages());
        return "userPanel";
    }

    @GetMapping("/change/email")
    public String updateEmail(@RequestParam(name = "email") String email, @RequestParam(name="repeatEmail") String repeatEmail, @AuthenticationPrincipal User user) {
        userService.changeEmail(email, repeatEmail, user.getId());
        return "redirect:/user-panel";
    }

    @PostMapping
    public String updateAddress(@Valid @ModelAttribute AddressDto addressDto, @AuthenticationPrincipal User user) {
        addressService.create(addressDto, user.getId());
        return "redirect:/user-panel";
    }

}
