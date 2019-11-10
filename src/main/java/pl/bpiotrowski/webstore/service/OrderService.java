package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.entity.OrderHeader;
import pl.bpiotrowski.webstore.entity.OrderItem;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.repository.OrderHeaderRepository;
import pl.bpiotrowski.webstore.repository.OrderItemRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public void placeOrder(HttpSession session, String username) {
        User purchaser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        Map<Product, Integer> order = (Map<Product, Integer>) session.getAttribute("shoppingCart");
        long lastNumber = (orderHeaderRepository.findMaxId() == null ? 0 : orderHeaderRepository.findMaxId());
        String number = (lastNumber + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);
        OrderHeader orderHeader = OrderHeader.builder().number(number).purchaser(purchaser).build();

        orderHeaderRepository.save(orderHeader);
        for(Map.Entry<Product, Integer> entry : order.entrySet()) {
            OrderItem orderItem = OrderItem.builder().orderHeader(orderHeader).item(entry.getKey()).quantity(entry.getValue()).build();
            orderItemRepository.save(orderItem);
        }

        session.removeAttribute("shoppingCart");
    }

}
