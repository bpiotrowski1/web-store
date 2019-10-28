package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.repository.ProductRepository;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void create(ProductDto dto) {
        Product entity = mapDtoToEntity(dto);
        productRepository.save(entity);
    }

    private Product mapDtoToEntity(ProductDto dto) {
        Product entity = new Product();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setThumbnail(dto.getThumbnail());
        entity.setCategory(dto.getCategory());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}
