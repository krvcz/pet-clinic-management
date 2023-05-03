package pl.ssanko.petclinic.data.common;

import pl.ssanko.petclinic.data.entity.Event;

import java.util.function.Supplier;

public enum EventType  {
    WIZYTA,
    INNE,
    SPOTKANIE;

    public String getColor() {

        return switch (this) {
            case WIZYTA -> "#ff3333";
            case INNE ->  "#ffff33";
            case SPOTKANIE -> "#33ff33";
            };
    }


}
