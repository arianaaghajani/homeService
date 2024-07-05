package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.enums.Role;
import ir.ariana.hw19spring.exception.DuplicateInformationException;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@Nested
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    private static Customer correctCustomer;
    private static Customer correctSecondCustomer;
    private static Customer duplicateEmailCustomer;





    @BeforeAll
    static void saveCustomer() {
        correctCustomer = Customer.builder()
                .firstName("ariana")
                .lastName("aghajani")
                .email("ariana@gmail.com")
                .password("aaBA14!@")
                .role(Role.CUSTOMER)
                .registrationTime(LocalDateTime.now())
                .build();

        correctSecondCustomer = Customer.builder()
                .firstName("samane")
                .lastName("aghajani")
                .email("sama@gmail.com")
                .password("aaCA15!@")
                .role(Role.CUSTOMER)
                .registrationTime(LocalDateTime.now())
                .build();

        duplicateEmailCustomer  = Customer.builder()
                .firstName("ariana")
                .lastName("aghajani")
                .email("ariana@gmail.com")
                .password("aaBA14!@")
                .role(Role.CUSTOMER)
                .registrationTime(LocalDateTime.now())
                .build();
    }

    @DisplayName("test for customer save method")
    @Order(1)
    @Test
    public void saveCostumer() {
        customerService.saveCustomer(correctCustomer);
        customerService.saveCustomer(correctSecondCustomer);
        Assertions.assertEquals(correctCustomer.getEmail(), customerService.findById(1L).getEmail());
    }



    @DisplayName("test for not save customer for duplicate email")
    @Order(2)
    @Test()
    public void doNotSaveDuplicateCustomerEmail() {
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> customerService.saveCustomer(duplicateEmailCustomer));
        Assertions.assertEquals("duplicate email can not insert", exception.getMessage());
    }


    @DisplayName("test for signIn customer")
    @Order(6)
    @Test()
    public void signInCustomer() {
        String email = correctCustomer.getEmail();
        String password = correctCustomer.getPassword();
        Customer foundedCustomer = customerService.singInCustomer(email, password);
        Assertions.assertEquals(foundedCustomer.getEmail(), correctCustomer.getEmail());
    }


    @DisplayName("test for change a customer password")
    @Order(7)
    @Test
    void updatePassword() {
        Long id = 1L;
        String newPassword = "bxBC19!@";
        customerService.UpdatePassword(newPassword, id);
        Assertions.assertEquals(customerService.findById(id).getPassword(), newPassword);
    }


    @DisplayName("test for remove a customer")
    @Test
    void deleteCustomer() {
        customerService.deleteCustomer(1L);
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> customerService.findById(1L));
        Assertions.assertEquals("customer with id " + 1 + " not founded", exception.getMessage());
    }
}