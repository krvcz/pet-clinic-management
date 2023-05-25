package pl.ssanko.petclinic.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerStatsDto {

    private Long numberOfVisits;
    private Long numberOfPets;

    public CustomerStatsDto(Long numberOfVisits, Long numberOfPets) {
        this.numberOfVisits = numberOfVisits;
        this.numberOfPets = numberOfPets;
    }

}
