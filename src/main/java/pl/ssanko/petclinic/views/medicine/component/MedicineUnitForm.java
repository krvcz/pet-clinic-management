package pl.ssanko.petclinic.views.medicine.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.PropertyId;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.views.medicine.MedicineView;

import java.util.Optional;
import java.util.stream.Collectors;

public class MedicineUnitForm extends FormLayout {

    private Grid<MedicineUnit> medicineUnitGrid;

    private Button save = new Button("Zakończ");

    private Button newButton = new Button("Zapisz");

    private Button resetButton = new Button("Reset");
    @PropertyId("unit")
    private TextField unitNameTextField = new TextField("Jednostka");
    @PropertyId("price")
    private BigDecimalField priceBigDecimalField = new BigDecimalField("Cena");
    private MedicineService medicineService;
    private Medicine medicine;

    private MedicineView medicineView;

    protected BeanValidationBinder<MedicineUnit> binder = new BeanValidationBinder<>(MedicineUnit.class);

    public MedicineUnitForm (MedicineService medicineService, Medicine medicine, MedicineView medicineView) {
        this.medicineService = medicineService;
        this.medicine = medicine;
        this.medicineView = medicineView;

        medicineUnitGrid = new Grid<>();
        medicineUnitGrid.removeAllColumns();
        medicineUnitGrid.addColumn(MedicineUnit::getUnit).setHeader("Jednostka");
        medicineUnitGrid.addColumn(MedicineUnit::getPrice).setHeader("Cena");
        medicineUnitGrid.addComponentColumn(e -> {
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addClickListener(button -> {
                medicineService.deleteMedicineUnit(e);
                refreshGrid();
            });
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return deleteButton;
        });
        medicineUnitGrid.setItems(medicine.getMedicineUnits());
        medicineUnitGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        medicineUnitGrid.asSingleSelect().addValueChangeListener(e -> binder.setBean(e.getValue()));

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        save.setIcon(new Icon(VaadinIcon.CHECK));
        newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newButton.setIcon(new Icon(VaadinIcon.PLUS));
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetButton.setIcon(new Icon(VaadinIcon.ERASER));
        setColspan(medicineUnitGrid, 2);
        setColspan(save, 2);
//        setColspan(newButton, 2);

        priceBigDecimalField.setSuffixComponent(new Span("PLN"));

        newButton.addClickListener( e -> {
            if (binder.isValid()) {
                MedicineUnit medicineUnit = binder.getBean();
                medicine.attachMedicineUnit(medicineUnit);
                try {
                   medicineService.saveMedicineUnit(medicineUnit);
                } catch (NotUniqueException ex) {
                    Dialog dialog = new Dialog();
                    Button okButton = new Button("Rozumiem");
                    okButton.addClickListener(x -> dialog.close());
                    dialog.add(ex.getMessage());
                    dialog.add(okButton);
                    add(dialog);
                    dialog.open();
                }

            }
            refreshGrid();
        });

        resetButton.addClickListener( e -> {
            medicineUnitGrid.deselectAll();
            MedicineUnit medicineUnit = new MedicineUnit();
            medicineUnit.setMedicine(medicine);
            binder.setBean(medicineUnit);
        });


        MedicineUnit medicineUnit = new MedicineUnit();
        medicineUnit.setMedicine(medicine);

       binder.setBean(medicineUnit);

//        if (medicineUnit == null) {
//            setVisible(false);
//        } else {
//            setVisible(true);
//            medicineUnitGrid.focus();
//        }

        binder.bindInstanceFields(this);

        add(unitNameTextField, priceBigDecimalField, newButton, resetButton, medicineUnitGrid, save);


        save.addClickListener(e -> {
            cancel();
            medicineView.updateGrid();
            Notification.show("Jednostka i cena zaktualizowana!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

    }

    private void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();

    }

    private void refreshGrid() {

        medicineUnitGrid.setItems(query -> medicineService.getMedicineUnitsFromMedicine(PageRequest.of(query.getPage(), query.getPageSize()), medicine.getId()));
    }
}
