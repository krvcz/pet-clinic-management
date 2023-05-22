package pl.ssanko.petclinic.data.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.CustomerRepository;
import pl.ssanko.petclinic.data.repository.MedicalProcedureRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MedicalProcedureServiceValidator {

    private final MedicalProcedureRepository medicalProcedureRepository;

    private final Validator validator;

    public void validate(MedicalProcedure medicalProcedure) throws NotUniqueException {

        Set<ConstraintViolation<MedicalProcedure>> validate = validator.validate(medicalProcedure);

        StringBuilder stringBuilder = new StringBuilder();

        for (ConstraintViolation<MedicalProcedure> violation: validate) {
            stringBuilder.append(violation.getMessage()).append("\n");
        }

        if (medicalProcedureRepository.isNotUnique(medicalProcedure.getName())) {
            stringBuilder.append("Podobna procedura już istnieje!");
        }

        if (!stringBuilder.isEmpty()) {
            throw new NotUniqueException(stringBuilder.toString());
        }

    }
}