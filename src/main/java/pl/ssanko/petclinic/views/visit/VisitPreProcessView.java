package pl.ssanko.petclinic.views.visit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Event;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.entity.Veterinarian;
import pl.ssanko.petclinic.data.service.*;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.customer.component.CustomerAddForm;
import pl.ssanko.petclinic.views.customer.component.CustomerForm;
import pl.ssanko.petclinic.views.visit.component.*;


@PageTitle("Wizyta - rejestracja")
@Route(value = "visits/prepare", layout = MainLayout.class)
@PermitAll
public class VisitPreProcessView extends VerticalLayout implements HasUrlParameter<Long> {
    private final CustomerService customerService;
    private final VeterinarianService veterinarianService;
    private final PetService petService;
    private final SpeciesService speciesService;
    private final VisitService visitService;
    private final EventService eventService;
    private Stepper stepper;
    private Event event;


    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long eventId) {
        if (eventId != null) {
            this.event = eventService.getAppointment(eventId).orElse(null);
        }

        configure();

    }



    public VisitPreProcessView(CustomerService customerService, VeterinarianService veterinarianService,
                               PetService petService, SpeciesService speciesService,
                               VisitService visitService, EventService eventService) {
        this.customerService = customerService;
        this.veterinarianService = veterinarianService;
        this.petService = petService;
        this.speciesService = speciesService;
        this.visitService = visitService;
        this.eventService = eventService;


    }

    private void configure() {
        Step stepOne = new StepOne(this.veterinarianService);
        Step stepTwo = new StepTwo(this.customerService, this.petService, this.speciesService);
        Step stepThree = event != null ?
                new StepThree(this.petService, this.visitService, this.speciesService, this.event, this.eventService) :
                new StepThree(this.petService, this.visitService, this.speciesService);

        Step stepFour = new StepFour();
        Step stepFive = new StepFive();
        stepper = new Stepper(stepOne.getContent());

        stepper.addStep(stepOne);
        stepper.addStep(stepTwo);
        stepper.addStep(stepThree);
        stepper.addStep(stepFour);
        stepper.addStep(stepFive);

        add(stepper.generateComponent(), stepper.getCurrentContent());

    }

}



