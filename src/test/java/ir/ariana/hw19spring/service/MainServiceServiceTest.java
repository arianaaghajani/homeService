package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.InvalidInputInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.MainService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class MainServiceServiceTest {
    @Autowired
    private MainServiceService mainServiceService;
    private static MainService correctService;
    private static MainService secondCorrectService;
    private static MainService invalidInfoService;

    @BeforeAll
    public static void makeMainService() {
        correctService = MainService.builder()
                .name("nezafat manzel")
                .registrationTime(LocalDateTime.now())
                .build();

        secondCorrectService = MainService.builder()
                .name("tamirat")
                .registrationTime(LocalDateTime.now())
                .build();

        invalidInfoService = MainService.builder()
                .name("@#$hg")
                .registrationTime(LocalDateTime.now())
                .build();
    }

    @DisplayName("test for save a mainService")
    @Order(1)
    @Test
    public void saveMainService() {
        mainServiceService.saveMainService(correctService);
        Assertions.assertEquals(MainServiceService.findById(1L).getName(), correctService.getName());
    }

    @DisplayName("test for not save a duplicate name mainService")
    @Order(2)
    @Test
    public void dontSaveDuplicateMainServiceName() {
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> mainServiceService.saveMainService(correctService));
        Assertions.assertEquals("duplicate mainService name can not insert", exception.getMessage());
    }

    @DisplayName("test for can not find by mainService id")
    @Order(3)
    @Test()
    public void canNotFindByMainServiceId() {
        Long id = 3L;
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> mainServiceService.findById(id));
        Assertions.assertEquals("mainService with id " + id + " not founded", exception.getMessage());
    }

    @DisplayName("test for invalid input mainService")
    @Order(4)
    @Test()
    public void doNotSaveInvalidMainService() {
        Throwable exception = Assertions.assertThrows(InvalidInputInformationException.class,
                () -> mainServiceService.validate(invalidInfoService));
        Assertions.assertEquals("some of inputs are not valid", exception.getMessage());
    }

    @DisplayName("test for remove a mainService")
    @Order(6)
    @Test
    public void removeMainService() {
        mainServiceService.saveMainService(secondCorrectService);
        Long id = secondCorrectService.getId();
        mainServiceService.removeMainService(id);
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> mainServiceService.findById(id));
        Assertions.assertEquals("mainService with id " + id + " not founded", exception.getMessage());
    }

}
