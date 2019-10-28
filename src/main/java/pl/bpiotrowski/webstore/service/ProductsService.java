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
    private final ProductMapService productMapService;

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> dto = new ArrayList<>();
        for (Product entity : products) {
            dto.add(productMapService.mapEntityToDto(entity));
        }
        return dto;
    }

}
