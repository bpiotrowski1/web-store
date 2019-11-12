package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.OrderHeaderDto;
import pl.bpiotrowski.webstore.entity.OrderHeader;
import pl.bpiotrowski.webstore.entity.OrderItem;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.repository.OrderHeaderRepository;
import pl.bpiotrowski.webstore.repository.OrderItemRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public List<OrderHeaderDto> findAll() {
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAll();
        List<OrderHeaderDto> orderHeadersDto = new ArrayList<>();
        for(OrderHeader entity : orderHeaders) {
            orderHeadersDto.add(mapEntityToDto(entity));
        }
        return orderHeadersDto;
    }

    public void placeOrder(HttpSession session, String username) {
        User purchaser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        Map<Product, Integer> order = (Map<Product, Integer>) session.getAttribute("shoppingCart");
        long lastNumber = (orderHeaderRepository.findMaxId() == null ? 0 : orderHeaderRepository.findMaxId());
        String number = (lastNumber + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setNumber(number);
        orderHeader.setPurchaser(purchaser);

        orderHeaderRepository.save(orderHeader);
        for(Map.Entry<Product, Integer> entry : order.entrySet()) {
            OrderItem orderItem = OrderItem.builder().orderHeader(orderHeader).item(entry.getKey()).quantity(entry.getValue()).build();
            orderItemRepository.save(orderItem);
        }

        session.removeAttribute("shoppingCart");
    }

    private OrderHeaderDto mapEntityToDto(OrderHeader orderHeader) {
        return OrderHeaderDto.builder()
                .id(orderHeader.getId())
                .number(orderHeader.getNumber())
                .purchaser(orderHeader.getPurchaser()).build();
    }

}
