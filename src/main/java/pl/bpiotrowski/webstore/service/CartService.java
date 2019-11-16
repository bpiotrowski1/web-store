package pl.bpiotrowski.webstore.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.ProductRepository;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@SessionScope
public class CartService {

    private final ProductRepository productRepository;

    @Getter private Map<Product, Integer> cart = new HashMap<>();

    public void addProductToCart(Integer quantity, Long id) {
        Product toAdd = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));

        if(!cart.containsKey(toAdd)) {
            cart.put(toAdd, 1);
        } else if(quantity == null) {
            cart.put(toAdd, cart.get(toAdd) + 1);
        } else {
            cart.put(toAdd, quantity);
        }
    }

    public void removeProductFromCart(Long id) {
        Product toRemove = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));
        cart.remove(toRemove);
    }

}
