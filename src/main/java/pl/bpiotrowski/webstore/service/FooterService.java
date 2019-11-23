package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.FooterDto;
import pl.bpiotrowski.webstore.entity.Footer;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.FooterRepository;

@RequiredArgsConstructor
@Service
public class FooterService {

    private final FooterRepository footerRepository;

    public FooterDto findOne() {
        Footer footer = isInitialized();
        return mapEntityToDto(footer);
    }

    public void update(FooterDto dto) {
        Footer footer = isInitialized();
        footer.setFacebook(dto.getFacebook());
        footer.setInstagram(dto.getInstagram());
        footer.setTwitter(dto.getTwitter());
        footerRepository.save(footer);
    }

    private Footer isInitialized() {
        return footerRepository.findCount() > 0 ? footerRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Footer " + 1 + " not found")) : new Footer();
    }

    private FooterDto mapEntityToDto(Footer entity) {
        FooterDto dto = new FooterDto();
        dto.setFacebook(entity.getFacebook());
        dto.setInstagram(entity.getInstagram());
        dto.setTwitter(entity.getTwitter());
        return dto;
    }

}
