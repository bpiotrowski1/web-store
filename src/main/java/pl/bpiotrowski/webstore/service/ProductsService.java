package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductsService {

    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> dto = new ArrayList<>();
        for (Product entity : products) {
            dto.add(mapEntityToDto(entity));
        }
        return dto;
    }

    private ProductDto mapEntityToDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setThumbnail(product.getThumbnail());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());

        return dto;
    }

}
