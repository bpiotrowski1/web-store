package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.dto.RoleDto;
import pl.bpiotrowski.webstore.service.RoleService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public String addRole(Model model) {
        model.addAttribute("roleForm", new RoleDto());
        return "role";
    }

    @PostMapping
    public String createRole(RoleDto roleForm) {
        roleService.create(roleForm);
        return "redirect:/role";
    }
}
