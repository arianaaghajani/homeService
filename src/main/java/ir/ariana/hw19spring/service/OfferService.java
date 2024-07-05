package ir.ariana.hw19spring.service;

import ir.ariana.hw19spring.enums.OfferStatus;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.exception.WrongInputPriceException;
import ir.ariana.hw19spring.model.Offer;
import ir.ariana.hw19spring.model.SubService;
import ir.ariana.hw19spring.repository.OfferRepository;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Offer offer) {
        Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
        if (violations.isEmpty()) {
            offerRepository.save(offer);
            log.info("subDuty saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Offer> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
    }

    @Transactional
   public List<Offer>findOfferListByOrderIdBasedOnProposedPrice(Long orderId, OfferStatus offerStatus){
        List<Offer> offersList = offerRepository.findOfferListByOrderIdBasedOnProposedPrice(orderId, offerStatus);
        if (!offersList.isEmpty())
           return offersList;
       else
           throw new NullPointerException();
   }

    public boolean checkOfferPrice(Long price, SubService service) {
        Long serviceBasePrice = service.getBasePrice();
        if (price >= serviceBasePrice)
            return true;
        else throw new WrongInputPriceException("offer price can not be less than default price");
    }
    public Offer findById(Long id) {
        return offerRepository.findById(id).orElseThrow(() ->
                new NotFoundException("offer with id " + id + " not founded"));
    }


    @Transactional
    public List<Offer> findSpecialistOffers(Long specialistId, OfferStatus offerStatus) {
        List<Offer> offers = offerRepository.findOffersBySpecialistIdAndOfferStatus(specialistId,offerStatus);
        if (!offers.isEmpty())
            return offers;
        else
            throw new NullPointerException();
    }

    @Transactional
    public List<Offer> findOrderOffers(Order order) {
        List<Offer> offers = offerRepository.findByOrder(order);
        if (!offers.isEmpty())
            return offers;
        else
            throw new NullPointerException();
    }

    @Transactional
    public List<Offer> findOfferListByOrderIdBasedOnSpecialistScore(Long orderId, OfferStatus offerStatus) {
        List<Offer> offers = offerRepository.findOfferListByOrderIdBasedOnSpecialistScore(orderId,offerStatus);
        return offers.stream().sorted(Comparator.comparing(a -> a.getSpecialist().getScore())).collect(Collectors.toList());
    }


    public void updateOfferStatus(OfferStatus offerStatus, Offer offer) {
        offer.setOfferStatus(offerStatus);
        validate(offer);
    }

    public void Save(Offer offer) {
        offerRepository.save(offer);
    }

}
