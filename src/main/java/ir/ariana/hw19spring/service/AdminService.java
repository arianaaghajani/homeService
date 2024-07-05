package ir.ariana.hw19spring.service;


import ir.ariana.hw19spring.enums.SpecialistStatus;
import ir.ariana.hw19spring.exception.NotFoundException;
import ir.ariana.hw19spring.model.*;
import ir.ariana.hw19spring.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final SpecialistService specialistService;
    private final SubServiceService subServiceService;

    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public void adminSignIn(String email, String password) {
        adminRepository.findByEmailAndPassword(email, password).orElseThrow(() ->
                new NotFoundException("wrong username or password"));
    }

    public void addSpecialistToSubService(Specialist specialist, List<Long> subServiceId) {
        specialistService.updateSpecialistStatus(SpecialistStatus.CONFIRMED,specialist.getId());
        List<SubService> createSubServiceList = creatSpecialistSubServiceList(subServiceId);
        for (SubService subService : createSubServiceList) {
            specialist.getSubServiceList().add(subService);
        }
        specialistService.validate(specialist);
    }

    public List<SubService> creatSpecialistSubServiceList(List<Long> subServiceId) {
        List<SubService> subServices = new ArrayList<>();
        for (Long id : subServiceId) {
            SubService subService = subServiceService.findById(id);
            subServices.add(subService);
        }
        return subServices;
    }

    public void removeSpecialistFromSubService(Specialist specialist, Long subServiceId) {
        List<SubService> subServices = specialist.getSubServiceList();
        subServices.removeIf(subService -> subService.getId() == subServiceId);
        specialist.setSubServiceList(subServices);
        specialistService.validate(specialist);
    }
}
