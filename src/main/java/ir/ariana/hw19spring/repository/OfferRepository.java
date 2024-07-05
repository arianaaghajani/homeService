package ir.ariana.hw19spring.repository;

import ir.ariana.hw19spring.enums.OfferStatus;
import ir.ariana.hw19spring.model.Offer;
import ir.ariana.hw19spring.model.Specialist;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer,Long> {
    @Query(" select o from Offer o where o.order.id = :orderId and o.offerStatus= :offerStatus order by o.proposedPrice asc ")
    List<Offer> findOfferListByOrderIdBasedOnProposedPrice(Long orderId, OfferStatus offerStatus);

    @Query(" select o from Offer o where o.order.id = :orderId and o.offerStatus= :offerStatus order by o.specialist.score desc ")
    List<Offer> findOfferListByOrderIdBasedOnSpecialistScore(Long orderId, OfferStatus offerStatus);

    @Query(" select o from Offer o where o.specialist.id = :specialistId and o.offerStatus = :offerStatus ")
    List<Offer> findOffersBySpecialistIdAndOfferStatus(Long specialistId, OfferStatus offerStatus);

    @Query(" select o from Offer o where o.order.id = :orderId and o.offerStatus = :offerStatus ")
    Optional<Offer> findByOrderId(Long orderId, OfferStatus offerStatus);

    List<Offer> findByOrder(Order order);
    List<Offer> findByOfferStatus(OfferStatus offerStatus);
    Optional<Offer> findByOrderAndSpecialist(Order order, Specialist specialist);


}

