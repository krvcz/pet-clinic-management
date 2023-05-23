package pl.ssanko.petclinic.data.validator;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;
import pl.ssanko.petclinic.data.entity.VisitMedicine;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.MedicalProcedureRepository;
import pl.ssanko.petclinic.data.repository.MedicineRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MedicineServiceValidator {

    private final MedicineRepository medicineRepository;

    private final Validator validator;

    public void validate(Medicine medicine, Set<String> units) throws NotUniqueException {

        Set<ConstraintViolation<Medicine>> validate = validator.validate(medicine);

        StringBuilder stringBuilder = new StringBuilder();

        for (ConstraintViolation<Medicine> violation: validate) {
            stringBuilder.append(violation.getMessage()).append("\n");
        }

        if (medicineRepository.isNotUnique(medicine.getName(), medicine.getManufacturer(), units)) {
            stringBuilder.append("Podobny lek już istnieje!");
        }

        if (!stringBuilder.isEmpty()) {
            throw new NotUniqueException(stringBuilder.toString());
        }

    }
}
