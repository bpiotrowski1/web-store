package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.bpiotrowski.webstore.exception.BadFileExtensionException;
import pl.bpiotrowski.webstore.exception.FileEmptyException;
import pl.bpiotrowski.webstore.exception.FileTooLargeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static pl.bpiotrowski.webstore.statics.Constants.TEN_MB;
import static pl.bpiotrowski.webstore.statics.Constants.THUMBNAIL_FORMAT;

@RequiredArgsConstructor
@Service
@Slf4j
public class ThumbnailService {

    private final ProductService productService;

    public void save(Long id, MultipartFile thumbnail) {
        try {
            validate(thumbnail);
            Path path = Paths.get(thumbnail.getOriginalFilename()); //safe - validate method
            byte[] bytes = thumbnail.getBytes();
            Files.write(path, bytes);
            productService.changeThumbnail(id, path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Resource findThumbnail(Long id) {
        return new FileSystemResource(productService.getOne(id).getThumbnail());
    }

    private void validate(MultipartFile thumbnail) {
        if(thumbnail.isEmpty() || thumbnail.getOriginalFilename() == null) {
            throw new FileEmptyException();
        }
        if(!THUMBNAIL_FORMAT.contains(thumbnail.getContentType())) {
            log.info("Someone try to upload file with extension: " + thumbnail.getContentType());
            throw new BadFileExtensionException();
        }
        if(thumbnail.getSize() > TEN_MB) {
            throw new FileTooLargeException();
        }
    }

}
