package pl.ssanko.petclinic.data.service;

import org.vaadin.stefan.fullcalendar.Entry;
import pl.ssanko.petclinic.data.common.EventType;
import pl.ssanko.petclinic.data.dto.ProductDto;
import pl.ssanko.petclinic.data.entity.Event;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.VisitMedicalProcedure;
import pl.ssanko.petclinic.data.entity.VisitMedicine;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class ProductMapper{

    public ProductDto map(VisitMedicine visitMedicine) {
        ProductDto productDto = new ProductDto();

        productDto.setName(visitMedicine.getMedicine().getName());
        productDto.setPrice(visitMedicine.getMedicineUnit().getPrice());
        productDto.setType("Lek");
        productDto.setUnit(visitMedicine.getMedicineUnit().getUnit());
        productDto.setQuantity(visitMedicine.getQuantity());

        return productDto;

    }

    public ProductDto map(VisitMedicalProcedure visitMedicalProcedure) {
        ProductDto productDto = new ProductDto();

        productDto.setName(visitMedicalProcedure.getMedicalProcedure().getName());
        productDto.setPrice(visitMedicalProcedure.getMedicalProcedure().getPrice());
        productDto.setType(visitMedicalProcedure.getMedicalProcedure().getType());

        return productDto;

    }


}