package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.repository.ProductRepository;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public void create(ProductDto dto) {
        Product product = mapDtoToEntity(dto);
        productRepository.save(product);
    }

    private ProductDto mapEntityToDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setThumbnail(product.getThumbnail());
//        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());

        return dto;
    }

    private Product mapDtoToEntity(ProductDto dto) {
        Product entity = new Product();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setThumbnail(dto.getThumbnail());
//        entity.setCategory(dto.getCategory());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}
