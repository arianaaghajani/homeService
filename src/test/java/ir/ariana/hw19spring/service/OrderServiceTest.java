package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.enums.OrderStatus;
import ir.ariana.hw19spring.exception.WrongInputPriceException;
import ir.ariana.hw19spring.model.Customer;
import ir.ariana.hw19spring.model.Offer;
import ir.ariana.hw19spring.model.Order;
import ir.ariana.hw19spring.model.SubService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SubServiceService subServiceService;
    @Autowired
    OfferService offerService;
    @Autowired
    SpecialistService specialistService;


    private static Order firstOrder;
    private static Order secondOrder;
    private static Offer firstOffer;


    @BeforeAll
    public static void makeOrderAndOffer() {

        firstOrder = Order.builder()
                .registrationTime(LocalDateTime.now())
                .description("need a good person")
                .executionTime(LocalDateTime.of(2024, 6, 11, 13, 15, 15))
                .endTime(LocalDateTime.of(2024, 8, 11, 13, 15, 15))
                .build();

        secondOrder = Order.builder()
                .registrationTime(LocalDateTime.now())
                .description("need a good person")
                .executionTime(LocalDateTime.of(2024, 6, 11, 13, 15, 15))
                .endTime(LocalDateTime.of(2024, 8, 11, 13, 15, 15))
                .build();

        firstOffer = Offer.builder()
                .proposedPrice(100000L)
                .executionTime(LocalDateTime.of(2024, 8, 10, 13, 19, 15))
                .endTime(LocalDateTime.of(2024, 6, 11, 13, 15, 15))
                .registrationTime(LocalDateTime.now())
                .build();
    }


    @DisplayName("test for save a correct order")
    @org.junit.jupiter.api.Order(1)
    @Test
    void saveOrder() {
        Customer customer = customerService.findById(2L);
        SubService subService = subServiceService.findById(1L);
        firstOrder.setCustomer(customer);
        firstOrder.setSubService(subService);
        firstOrder.setProposedPrice(150000L);
        orderService.SaveOrder(firstOrder);
        Assertions.assertEquals(orderService.findById(1L),customer.getId());
        Assertions.assertEquals(orderService.findById(1L),subService.getId());

    }


    @DisplayName("test for do not save an order with a price less than base price")
    @org.junit.jupiter.api.Order(2)
    @Test
    void checkOrderPrice() {
        Customer customer = customerService.findById(1L);
        SubService subService = subServiceService.findById(1L);
        firstOrder.setCustomer(customer);
        firstOrder.setSubService(subService);
        firstOrder.setProposedPrice(400L);
        Throwable exception = Assertions.assertThrows(WrongInputPriceException.class,
                () -> orderService.SaveOrder(firstOrder));
        Assertions.assertEquals("order price can not be less than base price", exception.getMessage());

    }

    @Test
    void findCustomerOrders() {
    }
}