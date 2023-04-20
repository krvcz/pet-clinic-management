package pl.ssanko.petclinic.views.customer;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.ssanko.petclinic.views.MainLayout;

@PageTitle("Add Customers")
@Route(value = "addcustomers", layout = MainLayout.class)
@PermitAll
public class AddCustomerView {
}
