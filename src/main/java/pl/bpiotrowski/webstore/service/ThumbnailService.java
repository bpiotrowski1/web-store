package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class ThumbnailService {

    private final ProductService productService;

    public void save(Long id, MultipartFile thumbnail) {
        try {
            byte[] bytes = thumbnail.getBytes();
            Path path = Paths.get(thumbnail.getOriginalFilename());
            Files.write(path, bytes);
            productService.changeThumbnail(id, path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Resource findThumbnail(Long id) {
        return new FileSystemResource(productService.getOne(id).getThumbnail());
    }

}
