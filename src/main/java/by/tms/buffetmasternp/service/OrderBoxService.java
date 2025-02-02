package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.dto.BoxDto;
import by.tms.buffetmasternp.dto.OrderDto;
import by.tms.buffetmasternp.entity.Box;
import by.tms.buffetmasternp.entity.OrderBox;
import by.tms.buffetmasternp.entity.OrderItem;
import by.tms.buffetmasternp.enums.StatusOrder;
import by.tms.buffetmasternp.repository.AccountRepository;
import by.tms.buffetmasternp.repository.OrderItemRepository;
import by.tms.buffetmasternp.repository.OrderBoxRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class OrderBoxService {

    private final OrderBoxRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AccountRepository accountRepository;
    private final CloseDateService closeDateService;
    private final BoxService boxService;

    public OrderBoxService(OrderBoxRepository orderRepository, OrderItemRepository orderItemRepository,
                           AccountRepository accountRepository, CloseDateService closeDateService,
                           BoxService boxService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.accountRepository = accountRepository;
        this.closeDateService = closeDateService;
        this.boxService = boxService;
    }

    @Transactional
    public void save(OrderDto orderDto, List<BoxDto> boxDtos) {
        OrderBox order = new OrderBox();
        order.setAccount(accountRepository.findById(orderDto.getAccountId()).get());
        order.setPhone(orderDto.getPhone());
        order.setAddress(orderDto.getAddress());
        order.setPrice(orderDto.getPrice());
        order.setComment(orderDto.getComment());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH);
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(orderDto.getDate(), formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException(e.getMessage(), orderDto.getDate(), 0);
        }
        order.setDate(localDate);
        closeDateService.addCloseDate(orderDto.getDate());
        order.setStatus(StatusOrder.OPEN);
        orderRepository.save(order);
        for (BoxDto boxDto : boxDtos) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(1);
            orderItem.setBox(boxService.getBoxById(boxDto.getId()));
            orderItemRepository.save(orderItem);
        }
    }

    public List<OrderDto> findAllByStatus(StatusOrder statusOrder) {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<OrderBox> orderBoxes = orderRepository.findAllByStatusOrderByDateAsc(statusOrder);
        return getOrderDtos(orderDtos, orderBoxes);
    }

    public List<OrderDto> findAllByStatusAndAccountId(StatusOrder statusOrder, Long accountId) {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<OrderBox> orderBoxes = orderRepository.findAllByStatusAndAccountIdOrderByDateAsc(statusOrder, accountId);
        return getOrderDtos(orderDtos, orderBoxes);
    }

    private List<OrderDto> getOrderDtos(List<OrderDto> orderDtos, List<OrderBox> orderBoxes) {
        for (OrderBox orderBox : orderBoxes) {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(orderBox.getId());
            orderDto.setPhone(orderBox.getPhone());
            orderDto.setAddress(orderBox.getAddress());
            orderDto.setPrice(orderBox.getPrice());
            orderDto.setComment(orderBox.getComment());
            orderDto.setStatus(orderBox.getStatus());
            orderDto.setAccountId(orderBox.getAccount().getId());
            orderDto.setDate(orderBox.getDate().toString());
            List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderBox.getId());
            List<Box> boxes = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                Box box = boxService.getBoxById(orderItem.getBox().getId());
                boxes.add(box);
            }
            orderDto.setBoxes(boxService.convertBoxesToBoxDtos(boxes));
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public void closeOrder(Long id) {
        OrderBox orderBox = orderRepository.findById(id).get();
        orderBox.setStatus(StatusOrder.CLOSED);
        orderRepository.save(orderBox);
    }
}
