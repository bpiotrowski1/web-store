package pl.bpiotrowski.webstore.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.bpiotrowski.webstore.dto.FooterDto;
import pl.bpiotrowski.webstore.service.FooterService;

@RequiredArgsConstructor
@ControllerAdvice
public class FooterAdvice {

    private final FooterService footerService;

    @ModelAttribute("footer")
    public FooterDto getFooter() {
        return footerService.findOne();
    }

}
