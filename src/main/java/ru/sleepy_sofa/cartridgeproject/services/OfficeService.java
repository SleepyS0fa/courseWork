package ru.sleepy_sofa.cartridgeproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sleepy_sofa.cartridgeproject.models.Office;
import ru.sleepy_sofa.cartridgeproject.repositories.OfficeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeService {

    @Autowired
    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office getOfficeById(Long id) {
        return officeRepository.findById(id).orElseThrow();
    }

    public List<Office> getOffices() {
        return officeRepository.findAll();
    }

    public void addOffice(List<String> names) {
        List<Office> offices = names.stream()
                .map(name -> {
                    Office office = new Office();
                    office.setName(name);
                    return office;
                })
                .collect(Collectors.toList());
        officeRepository.saveAll(offices);
    }

    public void deleteOffice(List<Long> ids) {
        List<Office> offices = ids.stream()
                .map(this::getOfficeById)
                .collect(Collectors.toList());
        officeRepository.deleteAll(offices);
    }
}
