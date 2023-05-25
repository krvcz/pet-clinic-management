package pl.ssanko.petclinic.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetStatsDto {

    private Long numberOfVisits;

    public PetStatsDto(Long numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }
}
