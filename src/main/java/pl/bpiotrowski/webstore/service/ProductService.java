package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.ProductDto;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.ProductRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductDto getOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));
        return mapEntityToDto(product);
    }

    public void create(ProductDto dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User " + username + " not found"));
        Product entity = mapDtoToEntity(dto);
        entity.setUser(user);
        productRepository.save(entity);
    }

    public Integer getQuantity(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));
        return product.getQuantity();
    }

    public void changeQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));
        product.setQuantity(quantity);
        productRepository.save(product);
    }

    void reduceQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    private ProductDto mapEntityToDto(Product entity) {
        ProductDto dto = new ProductDto();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setThumbnail(entity.getThumbnail());
        dto.setCategory(entity.getCategory());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());

        return dto;
    }

    private Product mapDtoToEntity(ProductDto dto) {
        Product entity = new Product();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setThumbnail(dto.getThumbnail());
        entity.setCategory(dto.getCategory());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());

        return entity;
    }

}
