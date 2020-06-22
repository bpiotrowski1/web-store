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
        return mapEntityToDto(aboutRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("About " + 1 + " not found")));
    }

    public void save(AboutDto aboutDto) {
        About about = mapDtoToEntity(aboutDto);
        aboutRepository.save(about);
    }

    private About mapDtoToEntity(AboutDto aboutDto) {
        About about = new About();

        about.setId(aboutDto.getId());
        about.setContent(aboutDto.getContent());

        return about;
    }

    private AboutDto mapEntityToDto(About about) {
        AboutDto aboutDto = new AboutDto();

        aboutDto.setId(about.getId());
        aboutDto.setContent(about.getContent());

        return aboutDto;
    }

}
