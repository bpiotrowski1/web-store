package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.ProductRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final ProductRepository productRepository;

    public List<Product> findAll(HttpSession session) {
        return (List<Product>) session.getAttribute("shoppingCart");
    }

    public void addProductToCart(HttpSession session, Long id) {
        List<Product> cart = getCart(session);
        Product toAdd = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));
        cart.add(toAdd);

        session.setAttribute("shoppingCart", cart);
    }

    public void removeProductFromCart(HttpSession session, Long id) {
        List<Product> cart = getCart(session);
        Product toRemove = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));
        cart.remove(toRemove);

        session.setAttribute("shoppingCart", cart);
    }

    private List<Product> getCart(HttpSession session) {

        List<Product> cart = (List<Product>) session.getAttribute("shoppingCart");
        if(cart == null) {
            cart = new ArrayList<>();
        }

        return cart;
    }
}
