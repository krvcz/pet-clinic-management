package pl.ssanko.petclinic.views.medicine;





import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.medicine.component.MedicineDeleteForm;
import pl.ssanko.petclinic.views.medicine.component.MedicineForm;
import pl.ssanko.petclinic.views.medicine.component.MedicinesEditForm;

import java.util.stream.Stream;


@PageTitle("Leki")
@Route(value = "medicines", layout = MainLayout.class)
@PermitAll
public class MedicineView extends VerticalLayout {
    private Grid<Medicine> medicineGrid;
    private final MedicineService medicineService;

    private Button addButton = new Button("Dodaj lek", new Icon(VaadinIcon.FILE_ADD));

    private Button editButton = new Button("Edytuj lek", new Icon(VaadinIcon.EDIT));
    private Button deleteButton = new Button("Usuń lek", new Icon(VaadinIcon.ERASER));

    private TextField filter;



    public MedicineView(MedicineService medicineService) {
        this.medicineService = medicineService;

        filter = new TextField();
        filter.setPlaceholder("Szukaj");
        filter.setClearButtonVisible(true);
        filter.setPrefixComponent(new Icon(VaadinIcon.SEARCH));


        // Grid z procedurami
        medicineGrid = new Grid<>(Medicine.class);

        medicineGrid.removeAllColumns();
        medicineGrid.addColumn(e -> e.getId()).setHeader("Id");
        medicineGrid.addColumn(e -> e.getRegistrationNumber()).setHeader("Identyfikator").setAutoWidth(true);
        medicineGrid.addComponentColumn(e -> {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            BigDecimalField priceTextField = new BigDecimalField();
            priceTextField.setSuffixComponent(new Span("PLN"));
            priceTextField.setReadOnly(true);
            ComboBox<MedicineUnit> comboBox = new ComboBox<>();
            comboBox.setItems(e.getMedicineUnits());
            comboBox.setItemLabelGenerator(MedicineUnit::getUnit);

            MedicineUnit medicineUnit = e.getMedicineUnits().stream().findAny().get();
            priceTextField.setValue(medicineUnit.getPrice());
            comboBox.setValue(e.getMedicineUnits().stream().findAny().get());
            comboBox.addValueChangeListener(x -> priceTextField.setValue(x.getValue().getPrice()));
            horizontalLayout.add(comboBox, priceTextField);

            return horizontalLayout;
        }).setHeader("Jednostka i cena").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getName()).setHeader("Nazwa").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getManufacturer()).setHeader("Firma").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getComposition()).setHeader("Substancje aktywne").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getDosage()).setHeader("Dozowanie").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getAdministrationRoute()).setHeader("Sposób podania").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getContraindications()).setHeader("Przeciwskazania").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getSideEffects()).setHeader("Efekty uboczne").setAutoWidth(true);



        medicineGrid.setItems(query -> medicineService.getMedicines(PageRequest.of(query.getPage(), query.getPageSize())));

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        medicineGrid.addSelectionListener(e -> {
            if(e.getFirstSelectedItem().isPresent()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });


        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);


        addButton.addClickListener(e -> showEditMedicalProcedureForm(new Medicine()));

        editButton.addClickListener(e -> {
            if (medicineGrid.getSelectedItems().iterator().hasNext()) {
                Medicine medicine = medicineGrid.getSelectedItems().iterator().next();
                showEditMedicalProcedureForm(medicine);
            }
            updateGrid();

        });

        deleteButton.addClickListener(e -> {
            Medicine medicine = medicineGrid.getSelectedItems().iterator().next();
            showDeleteMedicalProcedureForm(medicine);
            updateGrid();

        });


        filter.addValueChangeListener(e -> updateGrid());

//        medicalProcedureGrid.getCrudLayout().addFilterComponent(filter);

//        filter.addValueChangeListener(e -> medicalProcedureGrid.refreshGrid());

//        medicalProcedureGrid.setFindAllOperation(() -> medicalProcedureService.getMedicalProcedures(Pageable.unpaged()).toList());

        add(new HorizontalLayout(filter, addButton, editButton, deleteButton), medicineGrid);


    }

    private void showEditMedicalProcedureForm(Medicine medicine) {
        MedicineForm medicineEditForm = new MedicinesEditForm(medicineGrid, medicineService, medicine);

        Dialog dialog = new Dialog();
        dialog.add(medicineEditForm);

        dialog.open();

    }


    public void showDeleteMedicalProcedureForm(Medicine medicine) {
        MedicineForm medicineDeleteForm = new MedicineDeleteForm(medicineGrid, medicineService, medicine) {
        };

        Dialog dialog = new Dialog();
        dialog.add(medicineDeleteForm);

        dialog.open();

    }

    public void updateGrid() {
        medicineGrid.setItems(query ->
                medicineService.getMedicinesByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter.getValue()));
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

}



