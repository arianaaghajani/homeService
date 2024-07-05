package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.enums.Role;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.Admin;
import ir.ariana.hw19spring.model.Specialist;
import ir.ariana.hw19spring.model.SubService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class AdminTest {
    @Autowired
    AdminService adminService;
    @Autowired
    SubServiceService subServiceService;
    @Autowired
    SpecialistService specialistService;
    @Autowired
    MainServiceService mainServiceService;

    private static Admin admin;
    private static SubService subService;


    @BeforeAll
    static void saveAdmin() {
        admin = Admin.builder()
                .firstName("ali")
                .lastName("asadi")
                .password("aalA12!@")
                .email("pria@gmail.com")
                .role(Role.ADMIN)
                .registrationTime(LocalDateTime.now())
                .build();
        SubService.builder()
                .name("Nezafat")
                .basePrice(11000L)
                .description("this price is for an hour")
                .build();
    }

    @DisplayName("test for Save And SignIn Admin")
    @Order(1)
    @Test()
    public void adminSignIn() {
        adminService.saveAdmin(admin);
        adminService.adminSignIn(admin.getEmail(), admin.getPassword());
        Assertions.assertEquals(1, admin.getId());

    }

    @DisplayName("test for wrong signIn admin email")
    @Order(2)
    @Test()
    public void wrongSignInAdminUsername() {
        String email = "123456";
        String password = admin.getPassword();
        Throwable exception = assertThrows(NotFoundException.class,
                () -> adminService.adminSignIn(email, password));
        Assertions.assertEquals("wrong username or password", exception.getMessage());
    }

    @DisplayName("test for wrong signIn admin password")
    @Order(3)
    @Test()
    public void wrongSignInAdminPassword() {
        String email = admin.getEmail();
        String password = "a233";
        Throwable exception = assertThrows(NotFoundException.class,
                () -> adminService.adminSignIn(email, password));
        Assertions.assertEquals("wrong email or password", exception.getMessage());
    }


    @DisplayName("test for accept And Add Specialist ")
    @Order(5)
    @Test()
    public void addSpecialistToSubService() {
        Specialist specialist = specialistService.findById(1L);
        subService.setMainService(MainServiceService.findById(1L));
        subServiceService.saveSubService(subService);
        Long id = subService.getId();
        List<Long> subServiceId = new ArrayList<>();
        subServiceId.add(id);
        adminService.addSpecialistToSubService(specialist, subServiceId);
        List<SubService> subServices = specialist.getSubServiceList();
        Assertions.assertEquals(3, subServices.size());
    }

    @DisplayName("test for remove an specialist from subService")
    @Order(6)
    @Test()
    public void removeSpecialistFromSubService() {
        Specialist specialist = specialistService.findById(1L);
        adminService.removeSpecialistFromSubService(specialist, 4L);
        List<SubService> subServices = specialist.getSubServiceList();
        Assertions.assertEquals(2, subServices.size());
    }
}