package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.enums.Role;
import ir.ariana.hw19spring.enums.SpecialistStatus;
import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.Specialist;
import ir.ariana.hw19spring.model.SubService;
import ir.ariana.hw19spring.validation.CheckImage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@SpringBootTest
class SpecialistServiceTest {
    @Autowired
    SpecialistService specialistService;
    @Autowired
    SubServiceService subServiceService;
    @Autowired
    CheckImage checkImage;

    String correctPath = "C:\\Users\\ASUS\\Downloads\\20240705_102701_2003944260.jpg";

    private static Specialist correctSpecialist;
    private static Specialist deleteSpecialist;
    private static Specialist duplicateEmailSpecialist;


    @BeforeAll
    public static void makeSpecialist() {

        correctSpecialist = Specialist.builder()
                .firstName("sara")
                .lastName("moradi")
                .email("sara@gmail.com")
                .password("aaAA12!@")
                .province("tehran")
                .role(Role.SPECIALIST)
                .registrationTime(LocalDateTime.now())
                .build();

        deleteSpecialist = Specialist.builder()
                .firstName("sara")
                .lastName("moradi")
                .email("sara@gmail.com")
                .password("aaAA12!@")
                .province("tehran")
                .role(Role.SPECIALIST)
                .registrationTime(LocalDateTime.now())
                .build();


        duplicateEmailSpecialist = Specialist.builder()
                .firstName("sara")
                .lastName("moradi")
                .email("sara@gmail.com")
                .password("aaAA12!@")
                .province("tehran")
                .role(Role.SPECIALIST)
                .registrationTime(LocalDateTime.now())
                .build();


    }

    @DisplayName("test for save a correct specialist")
    @Order(1)
    @Test()
    public void saveCorrectImage() {
        byte[] image = CheckImage.specialistImage(correctPath);
        correctSpecialist.setImage(image);
        specialistService.saveSpecialist(correctSpecialist);
        Assertions.assertEquals(specialistService.findById(3L).getEmail(), correctSpecialist.getEmail());
    }

    @DisplayName("test for not save  for duplicate email")
    @Order(2)
    @Test()
    public void doNotSaveDuplicateSpecialistEmail() {
        byte[] image = CheckImage.specialistImage(correctPath);
        duplicateEmailSpecialist.setImage(image);
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> specialistService.saveSpecialist(duplicateEmailSpecialist));
        Assertions.assertEquals("duplicate email can not insert", exception.getMessage());
    }

    @DisplayName("test for signIn specialist")
    @Order(3)
    @Test()
    public void signInSpecialist() {
        String email = correctSpecialist.getEmail();
        String password = correctSpecialist.getPassword();
        Specialist foundedSpecialist = specialistService.signInSpecialist(email, password);
        Assertions.assertEquals(foundedSpecialist.getEmail(), correctSpecialist.getEmail());
    }


    @DisplayName("test for can not find by specialist id")
    @Order(4)
    @Test()
    public void canNotFindBySpecialistId() {
        Long id = 4L;
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> specialistService.findById(id));
        Assertions.assertEquals("expert with id " + id + " not founded", exception.getMessage());
    }

    @DisplayName("test for wrong signIn specialist email")
    @Order(5)
    @Test()
    public void wrongSignInSpecialistEmail() {
        String email = "sana@gmail.com";
        String password = correctSpecialist.getPassword();
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> specialistService.signInSpecialist(email, password));
        Assertions.assertEquals("wrong email or password", exception.getMessage());
    }

    @DisplayName("test for wrong signIn specialist password")
    @Order(6)
    @Test()
    public void wrongSignInSpecialistPassword() {
        String email = correctSpecialist.getEmail();
        String password = "456987";
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> specialistService.signInSpecialist(email, password));
        Assertions.assertEquals("wrong email or password", exception.getMessage());
    }

    @DisplayName("test for change an specialist password")
    @Order(7)
    @Test
    public void changeSpecialistPassword() {
        Long id = 3L;
        String newPassword = "bbBC12!@";
        specialistService.UpdatePassword(newPassword, id);
        Assertions.assertEquals(specialistService.findById(id).getPassword(), newPassword);
    }


    @DisplayName("test for change an specialist status")
    @Order(8)
    @Test
    public void changeSpecialistStatus() {
        Long id = 3L;
        SpecialistStatus specialistStatus =SpecialistStatus.CONFIRMED;
        specialistService.updateSpecialistStatus(specialistStatus, id);
        Assertions.assertEquals(specialistService.findById(id).getSpecialistStatus(),specialistStatus);
    }

    @DisplayName("test for find specialist by status")
    @Order(9)
    @Test()
    public void findBySpecialistStatus() {
        Long specialist = 1L;
        SpecialistStatus specialistStatus = SpecialistStatus.CONFIRMED;
        List<Specialist> specialists = specialistService.findBySpecialistStatus(specialistStatus);
        Assertions.assertEquals(specialist,specialists.size());
    }

    @DisplayName("test for check specialist access")
    @Order(10)
    @Test()
    public void checkSpecialistAccess() {
        Specialist specialist = specialistService.findById(3L);
        boolean access = specialistService.access(specialist);
        Assertions.assertTrue(access);
    }

}