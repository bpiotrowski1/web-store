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
import pl.bpiotrowski.webstore.exception.QuantityBelowZeroException;
import pl.bpiotrowski.webstore.repository.OrderHeaderRepository;
import pl.bpiotrowski.webstore.repository.OrderItemRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final CartService cartService;

    public List<OrderHeaderDto> findAll(String done) {
        List<OrderHeader> orderHeaders;
        if(done == null) {
            orderHeaders = orderHeaderRepository.findAll();
        } else {
            orderHeaders = orderHeaderRepository.findAllByDone(Boolean.parseBoolean(done));
        }
        List<OrderHeaderDto> orderHeadersDto = new ArrayList<>();
        for(OrderHeader entity : orderHeaders) {
            orderHeadersDto.add(mapOrderHeaderToDto(entity));
        }
        return orderHeadersDto;
    }

    public List<OrderHeaderDto> findAllByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User " + username + " not found"));
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAllByUserId(user.getId());
        List<OrderHeaderDto> result = new ArrayList<>();

        for (OrderHeader orderHeader : orderHeaders) {
            result.add(mapOrderHeaderToDto(orderHeader));
        }
        return result;
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

    public void placeOrder(String username) {
        User purchaser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        Map<Product, Integer> order = cartService.getCart();
        long lastNumber = (orderHeaderRepository.findMaxId() == null ? 0 : orderHeaderRepository.findMaxId());
        String number = (lastNumber + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setNumber(number);
        orderHeader.setPurchaser(purchaser);
        orderHeader.setDone(false);
        orderHeader.setDate(timeStamp);
        orderHeaderRepository.save(orderHeader);

        for(Map.Entry<Product, Integer> entry : order.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(entry.getValue());
            orderItem.setItem(entry.getKey());
            if(productService.getQuantity(orderItem.getItem().getId()) < orderItem.getQuantity()) {
                orderHeaderRepository.delete(orderHeader);
                throw new QuantityBelowZeroException(entry.getKey().getId());
            }
            orderItem.setOrderHeader(orderHeader);
            productService.reduceQuantity(orderItem.getItem().getId(), orderItem.getQuantity());
            orderItemRepository.save(orderItem);
        }

        cartService.getCart().clear();
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
                .date(orderHeader.getDate())
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
