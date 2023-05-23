package pl.ssanko.petclinic.views.medicine.component;

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
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;
import pl.ssanko.petclinic.data.service.MedicineService;

import java.util.ArrayList;
import java.util.List;

public abstract class MedicineForm extends FormLayout {

    @PropertyId("id")
    protected TextField idTextField = new TextField("Id");
    @PropertyId("name")
    protected TextField nameTextField = new TextField("Nazwa");
    @PropertyId("registrationNumber")
    protected TextField registrationNumberTextField = new TextField("Identyfikator");
    @PropertyId("composition")
    protected TextField compositionTextField = new TextField("Substancje aktywne");
    @PropertyId("dosage")
    protected TextField dosageTextField = new TextField("Dozowanie");
    @PropertyId("contraindications")
    protected TextField contraindicationsTextField = new TextField("Przeciwskaznia");
    @PropertyId("sideEffects")
    protected TextField sideEffectsTextField = new TextField("Efekty uboczne");
    @PropertyId("administrationRoute")
    protected TextField administrationRouteTextField = new TextField("Sposób podawania");
    @PropertyId("manufacturer")
    protected TextField manufacturerTextField = new TextField("Producent");
    @PropertyId("unit")
    protected TextField unitTextField = new TextField("Jednostka miary");
    @PropertyId("price")
    protected TextField priceTextField = new TextField("Cena");

    protected Button save  = new Button("Zapisz");

    protected Button cancel = new Button("Anuluj");
    protected BeanValidationBinder<Medicine> binder = new BeanValidationBinder<>(Medicine.class);

    protected MedicineService medicineService;

    protected Grid<Medicine> medicineGrid;

    protected Medicine medicine;


    public MedicineForm(Grid<Medicine> medicineGrid, MedicineService medicineService, Medicine medicine) {

        this.medicineGrid = medicineGrid;
        this.medicineService = medicineService;
        this.medicine = medicine;


        binder.setBean(medicine);

        if (medicine == null) {
            setVisible(false);
        } else {
            setVisible(true);
            nameTextField.focus();
        }

        binder.bindInstanceFields(this);
        priceTextField.setSuffixComponent(new Span("PLN"));

        idTextField.setReadOnly(true);
        setColspan(compositionTextField, 3);
        setColspan(dosageTextField, 3);
        setColspan(contraindicationsTextField, 3);
        setColspan(sideEffectsTextField, 3);
        setColspan(administrationRouteTextField, 3);
        setColspan(idTextField, 1);
        setResponsiveSteps(new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 3));

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        save.setIcon(new Icon(VaadinIcon.CHECK));
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        cancel.setIcon(new Icon(VaadinIcon.CLOSE));


        add(idTextField, nameTextField, manufacturerTextField, registrationNumberTextField, compositionTextField, dosageTextField,
                administrationRouteTextField, contraindicationsTextField, sideEffectsTextField, new HorizontalLayout(save, cancel));

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
        medicineGrid.setItems(query -> {
            var vaadinSortOrders = query.getSortOrders();
            var springSortOrders = new ArrayList<Sort.Order>();

            for (QuerySortOrder so : vaadinSortOrders) {
                String colKey = so.getSorted();
                if(so.getDirection() == SortDirection.DESCENDING) {
                    springSortOrders.add(Sort.Order.desc(colKey));
                }
            }
            return    medicineService.getMedicines(PageRequest.of(query.getPage(), query.getPageSize(), Sort.by(springSortOrders)));

        });
    }

}
