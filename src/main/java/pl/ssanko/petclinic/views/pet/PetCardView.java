package pl.ssanko.petclinic.views.pet;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.dto.PetStatsDto;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.StatsService;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.customer.CustomerCardView;
import pl.ssanko.petclinic.views.customer.CustomerView;
import pl.ssanko.petclinic.views.customer.component.CustomerCard;
import pl.ssanko.petclinic.views.pet.component.PetCard;

@PageTitle("Karta zwierzęcia")
@Route(value = "pets", layout = MainLayout.class)
@PermitAll
public class PetCardView extends VerticalLayout implements HasUrlParameter<Long> {

    private final StatsService statsService;

    private final PetService petService;

    private final VisitService visitService;

    private Pet pet;

    private PetStatsDto petStatsDto;


    public PetCardView (StatsService statsService, PetService petService, VisitService visitService) {
        this.statsService = statsService;
        this.petService = petService;
        this.visitService = visitService;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        this.pet = petService.getPetById(aLong);
        this.petStatsDto = statsService.getPetStats(aLong);

        configure();
        setWidthFull();
    }

    private void configure() {
        Button backCustomerGridButton = new Button("Pokaż właściciela", new Icon(VaadinIcon.CLIPBOARD_USER));

        backCustomerGridButton.addClickListener(e -> {
            backCustomerGridButton.getUI().ifPresent(ui -> ui.navigate(
                    CustomerCardView.class, pet.getCustomer().getId()));
        });

        backCustomerGridButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);



        add(backCustomerGridButton, new PetCard(pet, petStatsDto, visitService));
    }
}
