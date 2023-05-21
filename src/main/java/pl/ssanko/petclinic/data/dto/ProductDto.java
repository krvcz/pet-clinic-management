package pl.ssanko.petclinic.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    private String name;
    private String type;
    private String unit;
    private Double quantity;
    private BigDecimal price;


}
