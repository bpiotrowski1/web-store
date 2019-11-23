package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bpiotrowski.webstore.dto.FooterDto;
import pl.bpiotrowski.webstore.service.FooterService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/footer")
public class FooterController {

    private final FooterService footerService;

    @GetMapping
    public String changeFooter(Model model) {
        model.addAttribute("footerForm", footerService.findOne());
        return "admin/footer";
    }

    @PostMapping
    public String saveFooter(@ModelAttribute FooterDto footerDto) {
        footerService.update(footerDto);
        return "redirect:/admin/footer";
    }

}
