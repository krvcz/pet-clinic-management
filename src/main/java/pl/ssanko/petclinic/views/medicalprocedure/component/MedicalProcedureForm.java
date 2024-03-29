package pl.ssanko.petclinic.views.medicalprocedure.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.PropertyId;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;

import java.util.List;

public abstract class MedicalProcedureForm extends FormLayout {

    @PropertyId("id")
    protected TextField idTextField = new TextField("Id");
    @PropertyId("name")
    protected TextField nameTextField = new TextField("Nazwa");
    @PropertyId("type")
    protected ComboBox<String> typeComboBox = new ComboBox<String>("Typ");
    @PropertyId("description")
    protected TextField descriptionTextField = new TextField("Opis");
    @PropertyId("price")
    protected TextField priceTextField = new TextField("Cena");

    protected Button save  = new Button("Zapisz");

    protected Button cancel = new Button("Anuluj");
    protected BeanValidationBinder<MedicalProcedure> binder = new BeanValidationBinder<>(MedicalProcedure.class);

    protected MedicalProcedureService medicalProcedureService;

    protected Grid<MedicalProcedure> medicalProcedureGrid;

    protected MedicalProcedure medicalProcedure;


    public MedicalProcedureForm(Grid<MedicalProcedure> medicalProcedureGrid, MedicalProcedureService medicalProcedureService, MedicalProcedure medicalProcedure) {

        this.medicalProcedureGrid = medicalProcedureGrid;
        this.medicalProcedureService = medicalProcedureService;
        this.medicalProcedure = medicalProcedure;

        typeComboBox.setItems(List.of("RTG/USG", "Zabieg", "Badanie laboratoryjne"));

        binder.setBean(medicalProcedure);

        if (medicalProcedure == null) {
            setVisible(false);
        } else {
            setVisible(true);
            nameTextField.focus();
        }

        binder.bindInstanceFields(this);
        priceTextField.setSuffixComponent(new Span("PLN"));

        idTextField.setReadOnly(true);
        setColspan(descriptionTextField, 3);
        setColspan(idTextField, 3);
        setResponsiveSteps(new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 3));

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        save.setIcon(new Icon(VaadinIcon.CHECK));
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        cancel.setIcon(new Icon(VaadinIcon.CLOSE));


        add(idTextField, nameTextField, typeComboBox, priceTextField, descriptionTextField, new HorizontalLayout(save, cancel));

        cancel.addClickListener(e -> cancel());
        save.addClickListener(e -> {
            if (binder.isValid())
            {
                save();

            }
        });


    }


    protected abstract void cancel();

    protected abstract void save();


    protected void refreshGrid() {
        medicalProcedureGrid.setItems(query ->
                medicalProcedureService.getMedicalProcedures(PageRequest.of(query.getPage(), query.getPageSize())));
    }

}
