package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.exception.WrongInputPriceException;
import ir.ariana.hw19spring.model.Customer;
import ir.ariana.hw19spring.model.Offer;
import ir.ariana.hw19spring.model.Specialist;
import ir.ariana.hw19spring.model.SubService;
import ir.ariana.hw19spring.validation.CheckImage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class OfferServiceTest {

    @Autowired
    OfferService offerService;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SubServiceService subServiceService;
    @Autowired
    SpecialistService specialistService;
    @Autowired
    CheckImage CheckImage;

    private static Offer firstOffer;
    private static Offer secondOffer;
    private static Offer wrongPriceOffer;
    private static Offer duplicateOffer;

    private static Specialist correctSecondSpecialist;


    @BeforeAll
    public static void makeOffer() {

        firstOffer = Offer.builder()
                .proposedPrice(120000L)
                .executionTime(LocalDateTime.of(2024, 10, 11, 13, 15, 15))
                .endTime(LocalDateTime.of(2024, 10, 11, 15, 30))
                .build();

        secondOffer = Offer.builder()
                .proposedPrice(190000L)
                .executionTime(LocalDateTime.of(2024, 11, 11, 15, 15, 15))
                .endTime(LocalDateTime.of(2024, 10, 11, 17, 30))
                .build();

        wrongPriceOffer = Offer.builder()
                .proposedPrice(140000L)
                .executionTime(LocalDateTime.of(2024, 10, 11, 13, 15, 15))
                .endTime(LocalDateTime.of(2024, 10, 11, 15, 30))
                .build();

        duplicateOffer = Offer.builder()
                .proposedPrice(120000L)
                .executionTime(LocalDateTime.of(2024, 8, 11, 13, 15, 15))
                .endTime(LocalDateTime.of(2024, 8, 11, 15, 30))
                .build();


        correctSecondSpecialist = Specialist.builder()
                .firstName("ali")
                .lastName("mahbobi")
                .email("ali@gmail.com")
                .password("aaAA12!@")
                .build();
    }

    @DisplayName("test for save an  offer")
    @Order(1)
    @Test
    public void saveOffer() {
        Specialist specialist = specialistService.findById(3L);
        ir.ariana.hw19spring.model.Order order = orderService.findById(1L);
        firstOffer.setSpecialist(specialist);
        firstOffer.setOrder(order);
        offerService.Save(firstOffer);
        Assertions.assertEquals(offerService.findById(1L).getSpecialist().getEmail(),
                firstOffer.getSpecialist().getEmail());
    }


    @DisplayName("test for not save an offer that price is lower than default")
    @Order(2)
    @Test
    public void notAcceptOfferPrice() {
        Specialist specialist = specialistService.findById(3L);
        ir.ariana.hw19spring.model.Order order = orderService.findById(7L);
        wrongPriceOffer.setSpecialist(specialist);
        wrongPriceOffer.setOrder(order);
        Throwable exception = assertThrows(WrongInputPriceException.class,
                () -> offerService.Save(wrongPriceOffer));
        assertEquals("offer price can not be less than default price", exception.getMessage());
    }

    @DisplayName("test for do not save a duplicate offer")
    @Order(3)
    @Test
    public void doNotTakeDuplicateOffer() {
        Specialist specialist = specialistService.findById(3L);
        ir.ariana.hw19spring.model.Order order = orderService.findById(7L);
        duplicateOffer.setSpecialist(specialist);
        duplicateOffer.setOrder(order);
        Throwable exception = assertThrows(DuplicateInformationException.class,
                () -> offerService.Save(duplicateOffer));
        assertEquals("an order is exist by this specialist for this order", exception.getMessage());
    }

    @DisplayName("test for can not find an offer by id")
    @org.junit.jupiter.api.Order(4)
    @Test
    public void canNotFindById() {
        Long id = 6L;
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> offerService.findById(id));
        Assertions.assertEquals("offer with id " + id + " not founded", exception.getMessage());
    }



}