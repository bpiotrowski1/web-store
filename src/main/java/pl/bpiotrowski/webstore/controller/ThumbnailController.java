package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.bpiotrowski.webstore.service.ThumbnailService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/thumbnail")
public class ThumbnailController {

    private final ThumbnailService thumbnailService;

    @GetMapping
    public String getFrom() {
        return "admin/thumbnail";
    }

    @GetMapping(value = "/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Resource getThumbnail(@PathVariable(name = "productId") Long productId) {
        return thumbnailService.findThumbnail(productId);
    }

    @PostMapping
    public String uploadThumbnail(@RequestParam("id") Long id, @RequestParam("thumbnail") MultipartFile thumbnail) {
        thumbnailService.save(id, thumbnail);
        return "redirect:/admin/products";
    }

}
