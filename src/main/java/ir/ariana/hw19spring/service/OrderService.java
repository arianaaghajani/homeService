package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.enums.OrderStatus;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.exception.WrongInputPriceException;
import ir.ariana.hw19spring.model.Customer;
import ir.ariana.hw19spring.model.Offer;
import ir.ariana.hw19spring.model.Order;
import ir.ariana.hw19spring.model.SubService;
import ir.ariana.hw19spring.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OfferService offerService;


    public void SaveOrder(ir.ariana.hw19spring.model.Order order) {
        orderRepository.save(order);
    }

    public boolean checkOrderPrice(Long price, SubService subService) {
        Long subServicePrice = subService.getBasePrice();
        if (price >= subServicePrice)
            return true;
        else throw new WrongInputPriceException("order price can not be less than default price");
    }

    public List<ir.ariana.hw19spring.model.Order> findCustomerOrders(String email, OrderStatus orderStatus) {
        List<ir.ariana.hw19spring.model.Order> orders = orderRepository.findByCustomerEmailAndOrderStatus(email,orderStatus);
        if (!orders.isEmpty())
            return orders;
        else
            throw new NullPointerException("no order for this customer");
    }

    public ir.ariana.hw19spring.model.Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException("order with id " + id + " not founded"));
    }

    public List<ir.ariana.hw19spring.model.Order> findSubServiceOrders(String subServiceName, OrderStatus orderStatusOne, OrderStatus orderStatusTwo) {
        List<ir.ariana.hw19spring.model.Order> orders = orderRepository.findBySubServiceNameAndOrderStatus(subServiceName,orderStatusOne,orderStatusTwo);
        if (!orders.isEmpty())
            return orders;
        else
            throw new NullPointerException("this subDuty do not have any order yet");
    }

    @Transactional
    public void removeOrder(Long id) {
        Order order = findById(id);
        orderRepository.delete((ir.ariana.hw19spring.model.Order) order);
    }



}
