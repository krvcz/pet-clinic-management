package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class VisitDetail {
    @NotBlank(message = "Wartość nie może być pusta!")
    @Size(max = 10, message = "Przekroczono zakres znaków od 1 do 10!")
    private String weight;
    @NotBlank(message = "Wartość nie może być pusta!")
    @Size(max = 15, message = "Przekroczono zakres znaków od 1 do 15!")
    private String temperature;
    @Size(max = 255, message = "Przekroczono limit znaków 255!")
    private String comment;
    @NotBlank(message = "Wartość nie może być pusta!")
    @Size(max = 255, message = "Przekroczono limit znaków 255!")
    private String interview;
    @Column(name = "clinical_trials")
    @NotBlank(message = "Wartość nie może być pusta!")
    @Size(max = 255, message = "Przekroczono limit znaków 255!")
    private String clinicalTrials;
    @NotBlank(message = "Wartość nie może być pusta!")
    @Size(max = 255, message = "Przekroczono limit znaków 255!")
    private String diagnosis;
    @Size(max = 255, message = "Przekroczono limit znaków 255!")
    private String recommendations;

}
