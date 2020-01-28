package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static pl.bpiotrowski.webstore.statics.Constants.*;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final CartService cartService;

    public List<OrderHeaderDto> findAll(int p, String done) {
        Page<OrderHeader> page;
        if(done == null) {
             page = orderHeaderRepository.findAll(PageRequest.of(p, ORDERS_PAGE_SIZE));
        } else {
            page = orderHeaderRepository.findAllByDone(PageRequest.of(p, ORDERS_PAGE_SIZE), Boolean.parseBoolean(done));
        }
        List<OrderHeader> list = page.toList();
        List<OrderHeaderDto> dto = new ArrayList<>();

        for (OrderHeader orderHeader : list) {
            dto.add(mapOrderHeaderToDto(orderHeader));
        }

        return dto;
    }

    public List<String> pagesCount(Page<OrderHeader> page) {
        List<String> result = new ArrayList<>();
        int totalPages = page.getTotalPages();
        for (int i = 1; i <= totalPages; i++) {
            result.add(String.valueOf(i));
        }
        return result;
    }

    public List<String> getUserPanelTotalPages(Long id) {
        Page<OrderHeader> page = orderHeaderRepository.findAllByUserId(PageRequest.of(FIRST_PAGE, USER_PANEL_PAGE_SIZE), id);
        return pagesCount(page);
    }

    public List<String> getOrdersTotalPages() {
        Page<OrderHeader> page = orderHeaderRepository.findAll(PageRequest.of(FIRST_PAGE, ORDERS_PAGE_SIZE));
        return pagesCount(page);
    }

    public List<OrderHeaderDto> findAllByUserId(int p, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        Page<OrderHeader> page = orderHeaderRepository.findAllByUserId(PageRequest.of(p, USER_PANEL_PAGE_SIZE), user.getId());
        List<OrderHeader> list = page.toList();
        List<OrderHeaderDto> dto = new ArrayList<>();

        for(OrderHeader orderHeader : list) {
            dto.add(mapOrderHeaderToDto(orderHeader));
        }

        return dto;
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

    public void placeOrder(Long id) {
        User purchaser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User " + id + " not found"));
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
