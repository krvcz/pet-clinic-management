package pl.ssanko.petclinic.views.medicine;





import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.ThemeVariant;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.medicalprocedure.component.MedicalProcedureDeleteForm;
import pl.ssanko.petclinic.views.medicalprocedure.component.MedicalProcedureForm;
import pl.ssanko.petclinic.views.medicine.component.MedicineDeleteForm;
import pl.ssanko.petclinic.views.medicine.component.MedicineForm;
import pl.ssanko.petclinic.views.medicine.component.MedicineUnitForm;
import pl.ssanko.petclinic.views.medicine.component.MedicinesEditForm;

import java.util.ArrayList;
import java.util.stream.Collectors;
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
        medicineGrid.addColumn(e -> e.getId()).setHeader("Id").setKey("id").getSortOrder(SortDirection.DESCENDING);
        medicineGrid.addColumn(e -> e.getRegistrationNumber()).setHeader("Identyfikator").setAutoWidth(true);
        medicineGrid.addComponentColumn(this::configureColumnComponent).setHeader("Jednostka i cena")
                .setAutoWidth(true);

//        medicineGrid.setClassNameGenerator(item -> item.getMedicineUnits().size() == 0  ? "error" : "error");
        medicineGrid.addColumn(e -> e.getName()).setHeader("Nazwa").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getManufacturer()).setHeader("Firma").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getComposition()).setHeader("Substancje aktywne").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getDosage()).setHeader("Dozowanie").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getAdministrationRoute()).setHeader("Sposób podania").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getContraindications()).setHeader("Przeciwskazania").setAutoWidth(true);
        medicineGrid.addColumn(e -> e.getSideEffects()).setHeader("Efekty uboczne").setAutoWidth(true);


        medicineGrid.setSortableColumns("id");

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
        medicineGrid.getColumnByKey("id").setSortable(true);




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


        addButton.addClickListener(e -> showEditMedicineForm(new Medicine()));

        editButton.addClickListener(e -> {
            if (medicineGrid.getSelectedItems().iterator().hasNext()) {
                Medicine medicine = medicineGrid.getSelectedItems().iterator().next();
                showEditMedicineForm(medicine);
            }
            updateGrid();

        });

        deleteButton.addClickListener(e -> {
            Medicine medicine = medicineGrid.getSelectedItems().iterator().next();
            showDeleteMedicineForm(medicine);
            updateGrid();

        });

        medicineGrid.setPartNameGenerator(e -> {
            if (e.getMedicineUnits() == null)
                return "low-rating";
            return null;
        });


        filter.addValueChangeListener(e -> updateGrid());
        medicineGrid.setPageSize(15);
        medicineGrid.setHeight("500px"); // Ustawia wysokość Grid na 500 pikseli

//        medicalProcedureGrid.getCrudLayout().addFilterComponent(filter);

//        filter.addValueChangeListener(e -> medicalProcedureGrid.refreshGrid());

//        medicalProcedureGrid.setFindAllOperation(() -> medicalProcedureService.getMedicalProcedures(Pageable.unpaged()).toList());

        add(new HorizontalLayout(filter, addButton, editButton, deleteButton), medicineGrid);


    }

    private HorizontalLayout configureColumnComponent(Medicine e) {
        if (e.getMedicineUnits().size() != 0) {
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
            Button medicalUnitEditFormButton = new Button(new Icon(VaadinIcon.PLUS));
            medicalUnitEditFormButton.addClickListener(d -> showMedicineUnitsForm(e));
            horizontalLayout.add(medicalUnitEditFormButton, comboBox, priceTextField);

            return horizontalLayout;
        }
        Icon icon = new Icon(VaadinIcon.WARNING); // Tworzenie ikony
        icon.setColor("red");
        icon.setTooltipText("Dodaj jednostki i cenę, aby lek był dostępny!");
        Button medicalUnitAddFormButton = new Button("Dodaj jednostke i cene", new Icon(VaadinIcon.PLUS));
        medicalUnitAddFormButton.addClickListener(d -> showMedicineUnitsForm(e));
        medicalUnitAddFormButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return new HorizontalLayout(icon, medicalUnitAddFormButton);
    }

    private void showMedicineUnitsForm(Medicine medicine) {
        MedicineUnitForm medicineUnitForm = new MedicineUnitForm(medicineService, medicine) {
        };

        Dialog dialog = new Dialog();
        dialog.add(medicineUnitForm);

        dialog.open();
    }

    private void showEditMedicineForm(Medicine medicine) {
        MedicineForm medicineEditForm = new MedicinesEditForm(medicineGrid, medicineService, medicine);

        Dialog dialog = new Dialog();
        dialog.add(medicineEditForm);

        dialog.open();

    }


    public void showDeleteMedicineForm(Medicine medicine) {
        MedicineForm medicineDeleteForm = new MedicineDeleteForm(medicineGrid, medicineService, medicine) {
        };

        Dialog dialog = new Dialog();
        dialog.add(medicineDeleteForm);

        dialog.open();

    }

    public void updateGrid() {

        medicineGrid.setItems(query -> {
            var vaadinSortOrders = query.getSortOrders();
            var springSortOrders = new ArrayList<Sort.Order>();

            for (QuerySortOrder so : vaadinSortOrders) {
                String colKey = so.getSorted();
                if(so.getDirection() == SortDirection.DESCENDING) {
                    springSortOrders.add(Sort.Order.desc(colKey));
                }
            }
            return    medicineService.getMedicinesByFilter(PageRequest.of(query.getPage(), query.getPageSize(),
                    Sort.by(springSortOrders)),filter.getValue());

        });
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

}



