package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.customer.CustomerView;

import java.time.LocalDate;

public abstract class CustomerForm extends FormLayout {

    protected TextField firstName = new TextField("Imię");
    protected TextField lastName = new TextField("Nazwisko");
    protected TextField email = new TextField("Adres email");
    protected TextField phoneNumber = new TextField("Telefon");

    protected Button save = new Button("Zapisz");
    protected Button delete = new Button("Usuń");
    protected Button cancel = new Button("Anuluj");
    protected Button plusButton = new Button("Nowe zwierzę", new Icon(VaadinIcon.PLUS));
    protected Button editButton = new Button("Edytuj zwierzę", new Icon(VaadinIcon.EDIT));
    protected Button removeButton = new Button("Usuń zwierzę", new Icon(VaadinIcon.ERASER));

    private Grid<Pet> petGrid = new Grid<>(Pet.class);

    private Tabs tabs = new Tabs();

    private Tab petTab = new Tab("Pets");

    private Tab setTab = new Tab("Rozliczenia");

    private Tab visitsTab = new Tab("Wizyty");

    private BeanValidationBinder<Customer> binder = new BeanValidationBinder<>(Customer.class);

    protected CustomerService customerService;

    protected Customer customer;
    protected CustomerView customersView;


    public CustomerForm(CustomerView customersView, CustomerService customerService,  Customer customer) {
        this.customersView = customersView;
        this.customerService = customerService;

        setCustomer(customer);

        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        petGrid.setColumns("name", "species", "breed", "gender", "dateOfBirth");
        petGrid.getColumnByKey("name").setHeader("Imię");
        petGrid.getColumnByKey("species").setHeader("Gatunek");
        petGrid.getColumnByKey("breed").setHeader("Rasa");
        petGrid.getColumnByKey("gender").setHeader("Płeć");
        petGrid.getColumnByKey("dateOfBirth").setHeader("Data urodzenia");


        add(firstName, lastName, email, phoneNumber, configureTabs(), createButtonsLayout());


        cancel.addClickListener(e -> cancel());
        save.addClickListener(e -> {
            if (binder.isValid())
            {
                save();

            }
        });
        delete.addClickListener(e -> delete());

    }

    protected abstract void cancel();

    protected abstract void save();

    protected abstract void delete();

    private void setCustomer(Customer customer) {
        this.customer = customer;
        binder.setBean(customer);

        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
           firstName.focus();
        }
    }


    private HorizontalLayout createButtonsLayout() {
        return new HorizontalLayout(save, delete, cancel);
    }

    private VerticalLayout configureTabs() {
        VerticalLayout horizontalLayout = new VerticalLayout();
        petTab.add(petGrid);
        tabs.add(petTab, setTab, visitsTab);

        HorizontalLayout pages = new HorizontalLayout();
        pages.setSizeFull();
        pages.add(petGrid);

        setColspan(tabs, 2);
        setColspan(pages, 2);


        tabs.addSelectedChangeListener(e -> {
                    if (e.getSelectedTab().getLabel().equals("Pets")) {
                        pages.removeAll();
                        pages.add(petGrid);
                    }
                }

        );


        plusButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        plusButton.getElement().setAttribute("aria-label", "addPet");


        editButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        editButton.getElement().setAttribute("aria-label", "editPet");


        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeButton.getElement().setAttribute("aria-label", "deletePet");

        horizontalLayout.add(tabs, pages, new HorizontalLayout(plusButton, editButton, removeButton),  pages);
        setColspan(horizontalLayout, 2);



        return  horizontalLayout;
    }


}