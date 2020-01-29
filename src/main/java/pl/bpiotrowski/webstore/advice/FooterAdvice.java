package pl.bpiotrowski.webstore.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.bpiotrowski.webstore.dto.FooterDto;
import pl.bpiotrowski.webstore.service.FooterService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@ControllerAdvice
public class FooterAdvice {

    private final FooterService footerService;

    @ModelAttribute("footer")
    public FooterDto getFooter() {
        return footerService.findOne();
    }

    @ModelAttribute("active")
    public String getUrl(HttpServletRequest request) {
        return String.valueOf(request.getRequestURI());
    }

}
