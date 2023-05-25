package pl.ssanko.petclinic.views.customer;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.customer.component.CustomerCard;

@PageTitle("Karta klienta")
@Route(value = "customers", layout = MainLayout.class)
@PermitAll
public class CustomerCardView extends VerticalLayout implements HasUrlParameter<Long> {

    private final CustomerService customerService;

    private Customer customer;

    private CustomerStatsDto customerStatsDto;


    public CustomerCardView (CustomerService customerService) {
        this.customerService = customerService;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        this.customer = customerService.getCustomerById(aLong);
        this.customerStatsDto = customerService.getCustomerStat(aLong);

        configure();
    }

    private void configure() {
        add(new CustomerCard(customer, customerStatsDto));
    }
}
