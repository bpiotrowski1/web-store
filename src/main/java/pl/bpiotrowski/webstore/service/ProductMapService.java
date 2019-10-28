package pl.bpiotrowski.webstore.service;

import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.entity.Product;

@Service
public class ProductMapService {

    public ProductDto mapEntityToDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setThumbnail(product.getThumbnail());
//        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());

        return dto;
    }

    public Product mapDtoToEntity(ProductDto dto) {
        Product entity = new Product();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setThumbnail(dto.getThumbnail());
//        entity.setCategory(dto.getCategory());
        entity.setPrice(dto.getPrice());

        return entity;
    }

}
