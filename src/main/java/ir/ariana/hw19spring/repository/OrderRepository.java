package ir.ariana.hw19spring.repository;

import ir.ariana.hw19spring.enums.OrderStatus;
import ir.ariana.hw19spring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Override
    boolean existsById(Long id);

    @Query(" select o from Order o where o.customer.email = :email and o.orderStatus = :orderStatus ")
    List<Order> findOrderListByCustomerEmailAndOrderStatus(String email, OrderStatus orderStatus);


    @Query(" select o from Order o where o.subService.name= :subServiceName and" +
            " (o.orderStatus = :orderStatusOne or o.orderStatus = :orderStatusTwo)")
    List<Order> findBySubServiceNameAndOrderStatus(String subServiceName, OrderStatus orderStatusOne, OrderStatus orderStatusTwo);

    List<Order> findByCustomerEmailAndOrderStatus(String email, OrderStatus orderStatus);
    List<Order> findByOrderStatus(OrderStatus orderStatus);

}
