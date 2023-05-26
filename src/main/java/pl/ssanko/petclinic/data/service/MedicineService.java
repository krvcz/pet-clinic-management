package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;
import pl.ssanko.petclinic.data.entity.VisitMedicine;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.MedicineRepository;
import pl.ssanko.petclinic.data.repository.MedicineUnitRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicinesRepository;
import pl.ssanko.petclinic.data.validator.CustomerServiceValidator;
import pl.ssanko.petclinic.data.validator.MedicineServiceValidator;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;

    private final MedicineUnitRepository medicineUnitRepository;

    private final VisitsMedicinesRepository visitsMedicinesRepository;

    private final MedicineServiceValidator medicineServiceValidator;

    @Transactional(readOnly = true)
    public Stream<Medicine> getMedicines(Pageable pageable){
        return medicineRepository.findAllOrderedById(pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<Medicine> getMedicinesAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicinesRepository.findVisitMedicineByVisitId(visitId, pageable).stream().map(VisitMedicine::getMedicine);
    }

    @Transactional(readOnly = true)
    public Map<Long, MedicineUnit> getMedicineUnitsAssignToMedicineAndVisit(Pageable pageable, Long visitId) {
        return visitsMedicinesRepository.findVisitMedicineByVisitId(visitId, pageable).stream().collect(Collectors.toMap(e -> e.getMedicine().getId(), VisitMedicine::getMedicineUnit));
    }

    @Transactional(readOnly = true)
    public Map<Long, Double> getMedicineQuantityAssignToMedicineAndVisit(Pageable pageable, Long visitId) {
        return visitsMedicinesRepository.findVisitMedicineByVisitId(visitId, pageable).stream().collect(Collectors.toMap(e -> e.getMedicine().getId(), VisitMedicine::getQuantity));
    }

    @Transactional
    public Medicine saveMedicine(Medicine medicine) throws NotUniqueException {
        Optional<Medicine> persistedMedicineOptional = medicine.getId() == null ?
                Optional.empty() :  medicineRepository.findById(medicine.getId());

        if (persistedMedicineOptional.isPresent()) {
            Medicine persistedMedicine = persistedMedicineOptional.get();
            persistedMedicine.setMedicineUnits(medicine.getMedicineUnits() == null ? null : medicine.getMedicineUnits() );
            persistedMedicine.setComposition(medicine.getComposition());
            persistedMedicine.setDosage(medicine.getDosage());
            persistedMedicine.setManufacturer(medicine.getManufacturer());
            persistedMedicine.setAdministrationRoute(medicine.getAdministrationRoute());
            persistedMedicine.setContraindications(medicine.getContraindications());
            persistedMedicine.setSideEffects(medicine.getSideEffects());
            persistedMedicine.setRegistrationNumber(medicine.getRegistrationNumber());
            persistedMedicine.setName(medicine.getName());
            return medicineRepository.save(persistedMedicine);
        }



        Medicine newMedicine = new Medicine();
        newMedicine.setMedicineUnits(medicine.getMedicineUnits() == null ? null : medicine.getMedicineUnits() );
        newMedicine.setComposition(medicine.getComposition());
        newMedicine.setDosage(medicine.getDosage());
        newMedicine.setManufacturer(medicine.getManufacturer());
        newMedicine.setAdministrationRoute(medicine.getAdministrationRoute());
        newMedicine.setContraindications(medicine.getContraindications());
        newMedicine.setSideEffects(medicine.getSideEffects());
        newMedicine.setRegistrationNumber(medicine.getRegistrationNumber());
        newMedicine.setName(medicine.getName());


        medicineServiceValidator.validate(newMedicine);

        return medicineRepository.save(newMedicine);
    }

    @Transactional
    public MedicineUnit saveMedicineUnit(MedicineUnit medicineUnit) throws NotUniqueException {
        Optional<Medicine> persistedMedicineOptional = medicineUnit.getMedicine().getId() == null ?
                Optional.empty() :  medicineRepository.findById(medicineUnit.getMedicine().getId());

        if (persistedMedicineOptional.isPresent()) {
            Medicine persistedMedicine = persistedMedicineOptional.get();

            persistedMedicine.setDosage(medicineUnit.getMedicine().getDosage());
            persistedMedicine.setManufacturer(medicineUnit.getMedicine().getManufacturer());
            persistedMedicine.setContraindications(medicineUnit.getMedicine().getContraindications());
            persistedMedicine.setComposition(medicineUnit.getMedicine().getComposition());
            persistedMedicine.setName(medicineUnit.getMedicine().getName());
            persistedMedicine.setSideEffects(medicineUnit.getMedicine().getSideEffects());
            persistedMedicine.setRegistrationNumber(medicineUnit.getMedicine().getRegistrationNumber());
            persistedMedicine.setAdministrationRoute(medicineUnit.getMedicine().getAdministrationRoute());
            persistedMedicine.attachMedicineUnit(medicineUnit);

            return medicineUnitRepository.save(medicineUnit);

        }

        MedicineUnit newmedicineUnit = new MedicineUnit();
//        medicineUnit.getMedicine().attachMedicineUnit(newmedicineUnit);
        newmedicineUnit.getMedicine().setDosage(medicineUnit.getMedicine().getDosage());
        newmedicineUnit.getMedicine().setManufacturer(medicineUnit.getMedicine().getManufacturer());
        newmedicineUnit.getMedicine().setContraindications(medicineUnit.getMedicine().getContraindications());
        newmedicineUnit.getMedicine().setComposition(medicineUnit.getMedicine().getComposition());
        newmedicineUnit.getMedicine().setName(medicineUnit.getMedicine().getName());
        newmedicineUnit.getMedicine().setSideEffects(medicineUnit.getMedicine().getSideEffects());
        newmedicineUnit.getMedicine().setRegistrationNumber(medicineUnit.getMedicine().getRegistrationNumber());
        newmedicineUnit.getMedicine().setAdministrationRoute(medicineUnit.getMedicine().getAdministrationRoute());
        newmedicineUnit.getMedicine().attachMedicineUnit(medicineUnit);


        medicineServiceValidator.validate(newmedicineUnit.getMedicine());

        return medicineUnitRepository.save(medicineUnit);
    }


    public Stream<Medicine> getMedicinesByFilter(Pageable pageable, String filter) {
        return medicineRepository.findAllByFilter(pageable, filter).stream();
    }

    public Stream<MedicineUnit> getMedicinesWithUnits(Pageable pageable) {
        return medicineUnitRepository.findAllWithUnits(pageable).stream();
    }

    public void deleteMedicine(Medicine medicine) {
        medicine.setActive(false);
        medicineRepository.save(medicine);
    }

    public Stream<MedicineUnit> getMedicineUnitsFromMedicine(Pageable pageable, Long medicineId) {
        return medicineUnitRepository.findAllByMedicineId(pageable, medicineId).stream();
    }

    public void deleteMedicineUnit(MedicineUnit medicineUnit) {
        MedicineUnit oldMedicineUnit = medicineUnitRepository.findById(medicineUnit.getId()).get();

        oldMedicineUnit.getMedicine().detachMedicineUnit(oldMedicineUnit);

        medicineUnitRepository.delete(oldMedicineUnit);

    }
}
