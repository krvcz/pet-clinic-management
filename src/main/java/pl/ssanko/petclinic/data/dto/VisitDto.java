package pl.ssanko.petclinic.data.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VisitDto {
    private Long id;
    private Long veterinarianId;
    private Long petId;
    private String description;
    private String date;
    private String status;
    private String basicInfo;
    private String comment;
    private String interview;
    private String clinicalTrails;
    private String diagnosis;
    private String recommendations;
    private String medicalProcedures;
    private String medicines;



}
