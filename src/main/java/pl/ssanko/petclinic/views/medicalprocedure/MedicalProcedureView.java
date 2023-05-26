package pl.ssanko.petclinic.views.medicalprocedure;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.medicalprocedure.component.MedicalProcedureEditForm;
import pl.ssanko.petclinic.views.medicalprocedure.component.MedicalProcedureForm;
import pl.ssanko.petclinic.views.medicalprocedure.component.MedicalProcedureDeleteForm;
@PageTitle("Usługi")
@Route(value = "medicalprocedures", layout = MainLayout.class)
@PermitAll
public class MedicalProcedureView extends VerticalLayout {

    private Grid<MedicalProcedure> medicalProcedureGrid;
    private final MedicalProcedureService medicalProcedureService;

    private Button addButton = new Button("Dodaj procedure", new Icon(VaadinIcon.FILE_ADD));

    private Button editButton = new Button("Edytuj procedure", new Icon(VaadinIcon.EDIT));
    private Button deleteButton = new Button("Usuń procedure", new Icon(VaadinIcon.ERASER));

    private TextField filter;



    public MedicalProcedureView(MedicalProcedureService medicalProcedureService) {
        this.medicalProcedureService = medicalProcedureService;

        filter = new TextField();
        filter.setPlaceholder("Szukaj...");
        filter.setClearButtonVisible(true);
        filter.setPrefixComponent(new Icon(VaadinIcon.SEARCH));


        // Grid z procedurami
        medicalProcedureGrid = new Grid<>(MedicalProcedure.class);

        medicalProcedureGrid.removeAllColumns();
        medicalProcedureGrid.addColumn(MedicalProcedure::getId).setHeader("Id");
        medicalProcedureGrid.addColumn(MedicalProcedure::getName).setHeader("Nazwa").setAutoWidth(true);
        medicalProcedureGrid.addColumn(MedicalProcedure::getType).setHeader("Typ").setAutoWidth(true);
        medicalProcedureGrid.addColumn(MedicalProcedure::getDescription).setHeader("Opis").setAutoWidth(true);
        medicalProcedureGrid.addColumn(MedicalProcedure::getPrice).setHeader("Cena").setAutoWidth(true);

        medicalProcedureGrid.setItems(query -> medicalProcedureService.getMedicalProcedures(PageRequest.of(query.getPage(), query.getPageSize())));

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        medicalProcedureGrid.asSingleSelect().addValueChangeListener(e -> {
            editButton.setEnabled(e.getValue() != null);
            deleteButton.setEnabled(e.getValue() != null);
        });



        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);


        addButton.addClickListener(e -> showEditMedicalProcedureForm(new MedicalProcedure()));

        editButton.addClickListener(e -> {
            if (medicalProcedureGrid.getSelectedItems().iterator().hasNext()) {
                MedicalProcedure medicalProcedure = medicalProcedureGrid.getSelectedItems().iterator().next();
                showEditMedicalProcedureForm(medicalProcedure);
            }
            updateGrid();

        });

            deleteButton.addClickListener(e -> {
                MedicalProcedure medicalProcedure = medicalProcedureGrid.getSelectedItems().iterator().next();
                showDeleteMedicalProcedureForm(medicalProcedure);
                updateGrid();

        });


            filter.addValueChangeListener(e -> updateGrid());

//        medicalProcedureGrid.getCrudLayout().addFilterComponent(filter);

//        filter.addValueChangeListener(e -> medicalProcedureGrid.refreshGrid());

//        medicalProcedureGrid.setFindAllOperation(() -> medicalProcedureService.getMedicalProcedures(Pageable.unpaged()).toList());

        add(new HorizontalLayout(filter, addButton, editButton, deleteButton), medicalProcedureGrid);

        medicalProcedureGrid.setHeight("800px");
    }

    private void showEditMedicalProcedureForm(MedicalProcedure medicalProcedure) {
        MedicalProcedureForm medicalProcedureEditForm = new MedicalProcedureEditForm(medicalProcedureGrid, medicalProcedureService, medicalProcedure);

        Dialog dialog = new Dialog();
        dialog.add(medicalProcedureEditForm);

        dialog.open();

    }


    public void showDeleteMedicalProcedureForm(MedicalProcedure medicalProcedure) {
        MedicalProcedureForm medicalProcedureDeleteForm = new MedicalProcedureDeleteForm(medicalProcedureGrid, medicalProcedureService, medicalProcedure) {
        };

        Dialog dialog = new Dialog();
        dialog.add(medicalProcedureDeleteForm );

        dialog.open();

    }

    public void updateGrid() {
        medicalProcedureGrid.setItems(query ->
                medicalProcedureService.getMedicalProceduresByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter.getValue()));
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
}