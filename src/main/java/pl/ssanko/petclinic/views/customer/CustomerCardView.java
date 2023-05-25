package pl.ssanko.petclinic.views.customer;

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
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;
import pl.ssanko.petclinic.data.service.StatsService;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.customer.component.CustomerCard;
import pl.ssanko.petclinic.views.visit.VisitView;
import pl.ssanko.petclinic.views.visit.component.VisitHistoryGrid;


@PageTitle("Karta klienta")
@Route(value = "customers", layout = MainLayout.class)
@PermitAll
public class CustomerCardView extends VerticalLayout implements HasUrlParameter<Long> {

    private final StatsService statsService;

    private final CustomerService customerService;

    private final VisitService visitService;

    private Customer customer;

    private CustomerStatsDto customerStatsDto;


    public CustomerCardView (StatsService statsService, CustomerService customerService, VisitService visitService) {
        this.statsService = statsService;
        this.customerService = customerService;
        this.visitService = visitService;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        this.customer = customerService.getCustomerById(aLong);
        this.customerStatsDto = statsService.getCustomerStats(aLong);

        configure();
        setWidthFull();
    }

    private void configure() {
        Button backCustomerGridButton = new Button("Powrót do listy klientów", new Icon(VaadinIcon.ARROW_CIRCLE_LEFT_O));

        backCustomerGridButton.addClickListener(e -> {
            backCustomerGridButton.getUI().ifPresent(ui -> ui.navigate(
                    CustomerView.class));
        });

        backCustomerGridButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        VisitHistoryGrid visitHistoryGrid = new VisitHistoryGrid();
        visitHistoryGrid.setItems(query -> visitService.getEntireInfoAboutVisitForCustomer(customer.getId(),
                PageRequest.of(query.getPage(), query.getPageSize())));

        add(backCustomerGridButton, new CustomerCard(customer, customerStatsDto), visitHistoryGrid);
    }
}
