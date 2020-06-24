package pl.bpiotrowski.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.bpiotrowski.webstore.service.ProductService;
import pl.bpiotrowski.webstore.service.ThumbnailService;

@RequiredArgsConstructor
@Controller
public class ThumbnailController {

    private final ThumbnailService thumbnailService;
    private final ProductService productService;

    @GetMapping("/admin/thumbnail")
    public String getForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("title", productService.getOne(id).getTitle());
        return "admin/thumbnail";
    }

    @GetMapping(value = "/thumbnail/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Resource getThumbnail(@PathVariable(name = "productId") Long productId) {
        return thumbnailService.findThumbnail(productId);
    }

    @PostMapping("/admin/thumbnail")
    public String uploadThumbnail(@RequestParam("id") Long id, @RequestParam("thumbnail") MultipartFile thumbnail) {
        thumbnailService.save(id, thumbnail);
        return "redirect:/admin/products?thumbnail_added";
    }

}
