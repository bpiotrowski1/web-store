package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.ProductRepository;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CartService {

    private final ProductRepository productRepository;

    public Map<Product, Integer> findAll(HttpSession session) {
        Map<Product, Integer> products = (Map<Product, Integer>) session.getAttribute("shoppingCart");

        if(products == null) {
            products = new HashMap<>();
        }

        return products;
    }

    public void addProductToCart(HttpSession session, Integer quantity, Long id) {
        Map<Product, Integer> cart = getCart(session);
        Product toAdd = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));

        if(!cart.containsKey(toAdd)) {
            cart.put(toAdd, 1);
        } else if(quantity == null) {
            cart.put(toAdd, cart.get(toAdd) + 1);
        } else {
            cart.put(toAdd, quantity);
        }

        session.setAttribute("shoppingCart", cart);
    }

    public void removeProductFromCart(HttpSession session, Long id) {
        Map<Product, Integer> cart = getCart(session);
        Product toRemove = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));
        cart.remove(toRemove);

        session.setAttribute("shoppingCart", cart);
    }

    private Map<Product, Integer> getCart(HttpSession session) {

        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("shoppingCart");
        if(cart == null) {
            cart = new HashMap<>();
        }

        return cart;
    }
}
