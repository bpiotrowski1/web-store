package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.OrderHeaderDto;
import pl.bpiotrowski.webstore.dto.OrderItemDto;
import pl.bpiotrowski.webstore.entity.OrderHeader;
import pl.bpiotrowski.webstore.entity.OrderItem;
import pl.bpiotrowski.webstore.entity.Product;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.OrderHeaderRepository;
import pl.bpiotrowski.webstore.repository.OrderItemRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.*;

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
            orderHeadersDto.add(mapOrderHeaderToDto(entity));
        }
        return orderHeadersDto;
    }

    public List<OrderItemDto> getItems(Long id) {
        OrderHeader orderHeader = orderHeaderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order header " + id + " not found"));
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderHeader(orderHeader);
        List<OrderItemDto> orderItemsDto = new ArrayList<>();

        for (OrderItem entity : orderItems) {
            orderItemsDto.add(mapOrderItemToDto(entity));
        }

        return orderItemsDto;
    }

    public OrderHeaderDto getHeader(Long id) {
        OrderHeader orderHeader = orderHeaderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order header " + id + " not found"));
        return mapOrderHeaderToDto(orderHeader);
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
        orderHeader.setDone(false);

        orderHeaderRepository.save(orderHeader);
        for(Map.Entry<Product, Integer> entry : order.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(entry.getKey());
            orderItem.setOrderHeader(orderHeader);
            orderItem.setQuantity(entry.getValue());
            orderItemRepository.save(orderItem);
        }

        session.removeAttribute("shoppingCart");
    }

    public void makeDone(Long id) {
        OrderHeader toDone = orderHeaderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found"));
        toDone.setDone(true);
        orderHeaderRepository.save(toDone);
    }

    public void undo(Long id) {
        OrderHeader toDone = orderHeaderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found"));
        toDone.setDone(false);
        orderHeaderRepository.save(toDone);
    }

    private OrderHeaderDto mapOrderHeaderToDto(OrderHeader orderHeader) {
        return OrderHeaderDto.builder()
                .id(orderHeader.getId())
                .number(orderHeader.getNumber())
                .purchaser(orderHeader.getPurchaser())
                .done(orderHeader.isDone())
                .build();
    }

    private OrderItemDto mapOrderItemToDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .item(orderItem.getItem())
                .orderHeader(orderItem.getOrderHeader())
                .build();
    }

}
