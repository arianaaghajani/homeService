package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.MainService;
import ir.ariana.hw19spring.model.SubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubServiceServiceTest {
    @Autowired
    MainServiceService mainServiceService;
    @Autowired
    SubServiceService subServiceService;


    private static SubService correctSubService;
    private static SubService secondCorrectSubService;
    private static SubService deleteSubService;
    private static SubService invalidInfoTypeSubService;


    @BeforeAll
    public static void makeSubService(){

        correctSubService = SubService.builder()
                .name("manzel")
                .basePrice(200000L)
                .registrationTime(LocalDateTime.now())
                .description("this price is for an hour")
                .build();

        secondCorrectSubService = SubService.builder()
                .name("daftar")
                .basePrice(70000L)
                .description("this price is for an hour")
                .registrationTime(LocalDateTime.now())
                .build();

        deleteSubService = SubService.builder()
                .name("sakhteman")
                .basePrice(200000L)
                .description("this price is for an hour")
                .registrationTime(LocalDateTime.now())
                .build();

        invalidInfoTypeSubService = SubService.builder()
                .name("!#$kld")
                .basePrice(110000L)
                .registrationTime(LocalDateTime.now())
                .build();
    }


    @DisplayName("test for save a subService")
    @Order(1)
    @Test
    public void saveSubService() {
        MainService mainService= mainServiceService.findById(1L);
        correctSubService.setMainService(mainService);
        subServiceService.saveSubService(correctSubService);
        Assertions.assertEquals(subServiceService.findById(1L).getName(), correctSubService.getName());
    }


    @DisplayName("test for save second subService")
    @Order(2)
    @Test
    public void saveSecondSubService() {
        MainService mainService = mainServiceService.findById(1L);
        secondCorrectSubService.setMainService(mainService);
        subServiceService.saveSubService(secondCorrectSubService);
        Assertions.assertEquals(subServiceService.findById(2L).getName(), secondCorrectSubService.getName());
    }

    @DisplayName("test for not save a duplicate subService")
    @Order(3)
    @Test
    public void dontSaveDuplicateSubService() {
        MainService mainService = MainServiceService.findById(1L);
        correctSubService.setMainService(mainService);
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> subServiceService.saveSubService(correctSubService));
        Assertions.assertEquals("duplicate subService name can not insert", exception.getMessage());
    }

    @DisplayName("test for can not find by subService id")
    @Order(4)
    @Test()
    public void canNotFindBySubServiceId() {
        Long id = 3L;
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> subServiceService.findById(id));
        Assertions.assertEquals("subDuty with id " + id + " not founded", exception.getMessage());
    }

    @DisplayName("test for update a subService price")
    @Order(6)
    @Test()
    public void updateSubServicePrice() {
        Long newPrice = 15000L;
        Long id = 1L;
        subServiceService.updateSubServicePrice(newPrice, id);
        Assertions.assertEquals(subServiceService.findById(id).getBasePrice(), newPrice);
    }

    @DisplayName("test for update a subService description")
    @Order(7)
    @Test()
    public void updateSubServiceDescription() {
        String newDescription = "this price is for two hours";
        Long id = 1L;
        subServiceService.updateSubServiceDescription(newDescription, id);
        Assertions.assertEquals(subServiceService.findById(id).getDescription(), newDescription);
    }

    @DisplayName("test for remove a subService")
    @Order(9)
    @Test
    public void removeSubService() {
        MainService mainService = MainServiceService.findById(1L);
        deleteSubService.setMainService(mainService);
        subServiceService.saveSubService(deleteSubService);
        Long id = deleteSubService.getId();
        subServiceService.removeSubService(deleteSubService);
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> subServiceService.findById(id));
        Assertions.assertEquals("subDuty with id " + id + " not founded", exception.getMessage());
    }
}