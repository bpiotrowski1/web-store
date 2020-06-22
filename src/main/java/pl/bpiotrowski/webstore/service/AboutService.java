package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.AboutDto;
import pl.bpiotrowski.webstore.entity.About;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.AboutRepository;

@RequiredArgsConstructor
@Service
public class AboutService {

    private final AboutRepository aboutRepository;

    public AboutDto findAbout() {
        About about = isInitialized();
        return mapEntityToDto(about);
    }

    public void save(AboutDto aboutDto) {
        About about = isInitialized();
        about.setContent(aboutDto.getContent());
        aboutRepository.save(about);
    }

    private About isInitialized() {
        return aboutRepository.findCount() > 0 ? aboutRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("About " + 1 + " not found")) : new About();
    }

    private AboutDto mapEntityToDto(About about) {
        AboutDto aboutDto = new AboutDto();

        aboutDto.setId(about.getId());
        aboutDto.setContent(about.getContent());

        return aboutDto;
    }

}
