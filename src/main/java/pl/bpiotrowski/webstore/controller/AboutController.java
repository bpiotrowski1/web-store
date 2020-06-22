package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.bpiotrowski.webstore.dto.AboutDto;
import pl.bpiotrowski.webstore.service.AboutService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AboutController {

    private final AboutService aboutService;

    @GetMapping("/about")
    public String getAbout(Model model) {
        model.addAttribute("about", aboutService.findAbout());
        return "about";
    }

    @GetMapping("/admin/about")
    public String editAbout(Model model) {
        model.addAttribute("aboutForm", new AboutDto());
        return "admin/editAbout";
    }

    @PostMapping("/admin/about")
    public String editAbout(@Valid @ModelAttribute AboutDto aboutDto) {
        aboutService.save(aboutDto);
        return "redirect:/about";
    }

}
