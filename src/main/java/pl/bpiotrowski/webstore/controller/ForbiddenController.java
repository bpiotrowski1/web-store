package pl.bpiotrowski.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forbidden")
public class ForbiddenController {

    @GetMapping
    public String forbiddenPage() {
        return "forbidden";
    }

}
