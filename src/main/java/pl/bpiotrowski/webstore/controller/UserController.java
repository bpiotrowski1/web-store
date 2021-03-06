package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bpiotrowski.webstore.dto.UserDto;
import pl.bpiotrowski.webstore.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new UserDto());
        return "register";
    }

    @GetMapping("/confirm")
    public String confirmUser(@RequestParam(name = "token") String token) {
        userService.activateUser(token);
        return "redirect:/login?confirmed";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDto userForm) {
        userService.create(userForm);
        return "redirect:/login?email=" + userForm.getEmail() + "&register_success";
    }

}
