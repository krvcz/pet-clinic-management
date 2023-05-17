package pl.ssanko.petclinic.views.visit;

import ch.qos.logback.classic.pattern.DateConverter;
import com.flowingcode.vaadin.addons.twincolgrid.TwinColGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.commons.lang3.time.CalendarUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.data.service.VeterinarianService;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Visits")
@Route(value = "visits/process", layout = MainLayout.class)
@PermitAll
public class VisitProcessView extends VerticalLayout implements HasUrlParameter<Long> {

    private final VisitService visitService;
    private final MedicineService medicineService;
    private Visit visit;
    // components for top section
    private Label visitNumberLabel;
    private Label customerIdLabel;
    private Label customerNameLabel;
    private Label customerPhoneLabel;
    private Label veterinarianIdLabel;
    private Label veterinarianNameLabel;
    private Label veterinarianSpecializationLabel;
    private Label customerEmailLabel;
    private Label petBreedLabel;
    private Label petSpeciesLabel;
    private Label petNameLabel;
    private Label petGenderLabel;
    private Label petDateOfBirthLabel;
    private Label petIdLabel;

    // tabs and contents
    private TabSheet tabSheet;
    private VerticalLayout treatmentLayout;
    private VerticalLayout medicationsLayout;
    private VerticalLayout labTestsLayout;
    private VerticalLayout imagingLayout;

    // components for treatment tab
    private TextArea weightTextArea;
    private TextArea tempertureTextArea;
    private TextArea commentTextArea;
    private TextArea symptomsTextArea;
    private TextArea medicalHistoryTextArea;
    private TextArea physicalExamTextArea;
    private TextArea diagnosisTextArea;
    private TextArea treatmentPlanTextArea;

    // components for medications tab

    private TwinColGrid<Medicine> medicinesGrid;
    private Button addMedicationButton;

    // components for lab tests tab
//    private Grid<LabTest> labTestGrid;
    private Button addLabTestButton;

    // components for imaging tab
//    private Grid<ImagingResult> imagingGrid;
    private Button addImagingResultButton;

    // component for visit history
    private Grid<Visit> visitGrid;

    public VisitProcessView(VisitService visitService, MedicineService medicineService) {

        this.visitService = visitService;
        this.medicineService = medicineService;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        this.visit = visitService.getVisitById(aLong);
        configure();
        configureCardsInfo();
        configureTabSheet();


    }

    private void configureTabSheet() {
        // Create tabs and contents
        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        treatmentLayout = new VerticalLayout();
        medicationsLayout = new VerticalLayout();
        labTestsLayout = new VerticalLayout();
        imagingLayout = new VerticalLayout();
        tabSheet.add("Karta leczenia", treatmentLayout);
        tabSheet.add("Leki", medicationsLayout);
        tabSheet.add("Badania laboratoryjne", labTestsLayout);
        tabSheet.add("RTG/USG", imagingLayout);

        configureTreatmentCard();
        configureMedicationsCard();

        add(tabSheet);
    }

    private void configureCardsInfo() {
        // utworzenie komponentów
        visitNumberLabel = new Label("Wizyta nr " + visit.getId());
        customerIdLabel = new Label("Id: " + String.valueOf(visit.getPet().getCustomer().getId()));
        customerNameLabel = new Label("Imię i nazwisko: " + visit.getPet().getCustomer().getFirstName() + " " + visit.getPet().getCustomer().getLastName());
        customerPhoneLabel = new Label("Telefon: " + visit.getPet().getCustomer().getPhoneNumber());
        customerEmailLabel = new Label("Email: " + visit.getPet().getCustomer().getEmail());
        veterinarianIdLabel = new Label("Id: " + String.valueOf(visit.getVeterinarian().getId()));
        veterinarianNameLabel = new Label("Imię i nazwisko: " + visit.getVeterinarian().getFirstName() + " " + visit.getVeterinarian().getLastName());
        veterinarianSpecializationLabel = new Label("Specjalizacja: " + visit.getVeterinarian().getSpecialization());

        petBreedLabel = new Label("Rasa: " + visit.getPet().getBreed());
        petSpeciesLabel = new Label("Gatunek: " + visit.getPet().getSpecies());
        petNameLabel = new Label("Imię: " + visit.getPet().getName());
        petGenderLabel = new Label("Płeć: " + visit.getPet().getGender());
        petDateOfBirthLabel = new Label("Data urodzenia: " + visit.getPet().getDateOfBirth() + " (" + ChronoUnit.YEARS.between(visit.getPet().getDateOfBirth(), LocalDate.now()) + " lat)");
        petIdLabel = new Label("Id: " + visit.getPet().getId());

        // ...

// utworzenie layoutu
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout layout1 = new VerticalLayout();
        layout1.addClassName("card");
        layout1.setMaxHeight("500px");
        layout1.setMargin(true);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.addClassName("card");
        layout2.setMaxHeight("500px");

        layout2.setMargin(true);

        VerticalLayout layout3 = new VerticalLayout();
        layout3.addClassName("card");
        layout3.setMaxHeight("500px");
        layout3.setMargin(true);


// dodanie komponentów do layoutu
        layout1.add(new VerticalLayout(new H3("Zwierzę:"), petIdLabel, petNameLabel, petSpeciesLabel, petBreedLabel, petGenderLabel, petDateOfBirthLabel));
        layout2.add(new VerticalLayout(new H3("Właściciel:"), customerIdLabel, customerNameLabel, customerPhoneLabel,  customerEmailLabel));
        layout3.add(new VerticalLayout(new H3("Specjalista:"), veterinarianIdLabel, veterinarianNameLabel, veterinarianSpecializationLabel));



        // stylowanie zawartości karty
        layout1.addClassName("content");
        layout2.addClassName("content");
        layout3.addClassName("content");

        horizontalLayout.add(layout1, layout2, layout3);


        // Create components for top section
        horizontalLayout.setWidthFull();
        add(visitNumberLabel, horizontalLayout);
    }

    private void configure() {


    }

    private void configureTreatmentCard() {
        weightTextArea = new TextArea("Waga");
        tempertureTextArea = new TextArea("Temperatura");
        commentTextArea = new TextArea("Komentarz");
        HorizontalLayout basicInfoLayout = new HorizontalLayout(weightTextArea, tempertureTextArea, commentTextArea);
        basicInfoLayout.setWidthFull();
        weightTextArea.setWidth("30%");
        tempertureTextArea.setWidth("30%");
        commentTextArea.setWidth("40%");

        symptomsTextArea = new TextArea("Wywiad");
        symptomsTextArea.setWidth("100%");
        symptomsTextArea.setHeight("100px");

        physicalExamTextArea = new TextArea("Badanie kliniczne");
        physicalExamTextArea.setWidth("100%");
        physicalExamTextArea.setHeight("100px");

        diagnosisTextArea = new TextArea("Rozpoznanie");
        diagnosisTextArea.setWidth("100%");
        diagnosisTextArea.setHeight("100px");


        treatmentPlanTextArea = new TextArea("Zalecenia");
        treatmentPlanTextArea.setWidth("100%");
        treatmentPlanTextArea.setHeight("100px");
        treatmentLayout.add(basicInfoLayout, symptomsTextArea, physicalExamTextArea, diagnosisTextArea, treatmentPlanTextArea);

        setSpacing(true);

    }

    private void configureMedicationsCard() {
         List<Medicine> medicineList =  medicineService.getMedicines().toList();
        medicinesGrid=
                new TwinColGrid<>(medicineList)
                        .addColumn(Medicine::getName, "Nazwa")
                        .addColumn(Medicine::getManufacturer, "Firma")
                        .addColumn(Medicine::getDosage, "Zalecane dozowanie")
                       .addColumn(Medicine::getComposition, "Substancje aktywne")
                        .withLeftColumnCaption("Dostępne leki")
                        .withRightColumnCaption("Wybrane leki")
                        .withoutRemoveAllButton()
                        .withDragAndDropSupport()
                        .withSizeFull()
                        .withOrientation(TwinColGrid.Orientation.VERTICAL_REVERSE)
                        .withoutAddAllButton()
                        .withoutRemoveAllButton();


            Button testB = new Button("Zapisz zmiany");


        Map<Medicine, MedicineUnit> map = new HashMap<>();
        Map<Medicine, BigDecimal> map2 = new HashMap<>();

            testB.addClickListener(event -> {
                    for (Medicine medicine : medicinesGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                        VisitMedicine visitMedicine = new VisitMedicine(visit, medicine, map.get(medicine), map2.get(medicine));
                        visitService.addNewMedicineToVisit(visitMedicine);
                }

                });

        // Rejestracja słuchacza dla Grid'a


        medicinesGrid.getAvailableGrid().getColumns().get(2).setAutoWidth(true);
        medicinesGrid.getSelectionGrid().getColumns().get(2).setAutoWidth(true);
        medicinesGrid.getAvailableGrid().getColumns().get(1).setAutoWidth(true);
        medicinesGrid.getSelectionGrid().getColumns().get(1).setAutoWidth(true);
        medicinesGrid.getAvailableGrid().getColumns().get(0).setAutoWidth(true);
        medicinesGrid.getSelectionGrid().getColumns().get(0).setAutoWidth(true);
            medicinesGrid.getSelectionGrid().addComponentColumn(e-> {
                ComboBox<MedicineUnit> comboBox = new ComboBox<>();
                comboBox.addValueChangeListener( x -> {
                    map.put(e, x.getValue());
                });
                comboBox.setItems(e.getMedicineUnits());
                return comboBox;
            }).setHeader("Jednostka").setKey("medicine_unit").setId("medicine_unit");

        medicinesGrid.getSelectionGrid().addComponentColumn(e-> {
            BigDecimalField numberField = new BigDecimalField();
            numberField.addValueChangeListener( x -> {
                 map2.put(e,  x.getValue());
            });
            return numberField;
        }).setHeader("Ilość").setKey("medicine_quantity").setId("medicine_quantity");



//         medicinesGrid.setItems();
         medicationsLayout.add(medicinesGrid, testB);
//         medicationsLayout.setMaxHeight("1000px");
         medicationsLayout.setSizeFull();
//        twinColGrid.setValue(selectedBooks);


    }

}