package pl.ssanko.petclinic.views.visit.component;

import com.flowingcode.vaadin.addons.twincolgrid.TwinColGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.data.service.VeterinarianService;
import pl.ssanko.petclinic.data.service.VisitService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StepFour extends Step{
    private final Integer ORDER = 4;
    private final Icon ICON = VaadinIcon.DOCTOR_BRIEFCASE.create();
    private final Span NAME = new Span("4. Wizyta");
    private TextField filterTextField;
    private VerticalLayout verticalLayout;
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
    private TextArea interviewTextArea;
    private TextArea medicalHistoryTextArea;
    private TextArea clinicalTrialTextArea;
    private TextArea diagnosisTextArea;
    private TextArea recommendationsTextArea;

    // components for medications tab

    private TwinColGrid<Medicine> medicinesGrid;

    private TwinColGrid<MedicalProcedure> medicalProcedureGrid;

    protected BeanValidationBinder<VisitDetail> binderVisitDetail = new BeanValidationBinder<>(VisitDetail.class);
    private Button addMedicationButton;

    // components for lab tests tab
//    private Grid<LabTest> labTestGrid;
    private Button addLabTestButton;

    // components for imaging tab
//    private Grid<ImagingResult> imagingGrid;
    private Button addImagingResultButton;

    // component for visit history
    private Grid<Visit> visitGrid;

    private  MedicineService medicineService;

    private MedicalProcedureService medicalProcedureService;

    private VisitService visitService;

    private Visit visit;

    public StepFour(MedicineService medicineService, MedicalProcedureService medicalProcedureService, VisitService visitService, Visit visit) {

        this.medicineService = medicineService;
        this.medicalProcedureService = medicalProcedureService;
        this.visitService = visitService;
        this.visit = visit;

    }

    public StepFour() {

    }
    @Override
    public void configure() {
        verticalLayout =  new VerticalLayout();
        configureCardsInfo();
        configureTabSheet();


        binderVisitDetail.bind(tempertureTextArea, VisitDetail::getTemperature,
                VisitDetail::setTemperature);

        binderVisitDetail.bind(weightTextArea, VisitDetail::getWeight,
                VisitDetail::setWeight);

        binderVisitDetail.bind(commentTextArea, VisitDetail::getComment,
                VisitDetail::setComment);

        binderVisitDetail.bind(clinicalTrialTextArea, VisitDetail::getClinicalTrials,
                VisitDetail::setClinicalTrials);

        binderVisitDetail.bind(recommendationsTextArea, VisitDetail::getRecommendations,
                VisitDetail::setRecommendations);

        binderVisitDetail.bind(diagnosisTextArea, VisitDetail::getDiagnosis,
                VisitDetail::setDiagnosis);

    }

    @Override
    public Integer getOrder() {
        return ORDER;
    }

    @Override
    public Icon getIcon() {
        return ICON;
    }

    @Override
    public Span getName() {
        return NAME;
    }

    @Override
    public Div getContent() {
        configure();
        return new Div(verticalLayout);
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
        configureMedicalProceduresCard();

        verticalLayout.add(tabSheet);
    }

    private void configureMedicalProceduresCard() {
        medicalProcedureGrid=  new TwinColGrid<MedicalProcedure>()
                .addColumn(MedicalProcedure::getName, "Nazwa")
                .addColumn(MedicalProcedure::getDescription, "Firma")
                .withLeftColumnCaption("Dostępne procedury")
                .withRightColumnCaption("Wybrane procedury")
                .withoutRemoveAllButton()
                .withDragAndDropSupport()
                .withSizeFull()
                .withOrientation(TwinColGrid.Orientation.VERTICAL_REVERSE)
                .withoutAddAllButton()
                .withoutRemoveAllButton();

        Button saveChangesButton = new Button("Zapisz zmiany");
        saveChangesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveChangesButton.addClickListener(event -> {
            visitService.removeVisitMedicalProcedure(visit.getId());
            for (MedicalProcedure medicalProcedure : medicalProcedureGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                VisitMedicalProcedure visitMedicalProcedure = new VisitMedicalProcedure(visit, medicalProcedure);
                visitService.addNewMedicalProcedureToVisit(visitMedicalProcedure);
            }
            Notification.show("Procedury zapisane");
        });

        medicalProcedureGrid.getAvailableGrid().getColumns().get(1).setAutoWidth(true);
        medicalProcedureGrid.getSelectionGrid().getColumns().get(1).setAutoWidth(true);
        medicalProcedureGrid.getAvailableGrid().getColumns().get(0).setAutoWidth(true);
        medicalProcedureGrid.getSelectionGrid().getColumns().get(0).setAutoWidth(true);

        medicalProcedureGrid.setItems(medicalProcedureService.getMedicalProcedures(Pageable.unpaged()));
        medicalProcedureGrid.setValue(medicalProcedureService.getMedicalProceduresAssignToVisit(Pageable.unpaged(), visit.getId()).collect(Collectors.toSet()));

        labTestsLayout.add(saveChangesButton, medicalProcedureGrid);
        labTestsLayout.setSizeFull();
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
        verticalLayout.add(new HorizontalLayout(visitNumberLabel, VisitCommonComponent.createStatusIcon(visit.getStatus())), horizontalLayout);
    }
    private void configureTreatmentCard() {
        Button saveChangesButton = new Button("Zapisz zmiany");
        saveChangesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        weightTextArea = new TextArea("Waga");
        tempertureTextArea = new TextArea("Temperatura");
        commentTextArea = new TextArea("Komentarz");
        HorizontalLayout basicInfoLayout = new HorizontalLayout(weightTextArea, tempertureTextArea, commentTextArea);
        basicInfoLayout.setWidthFull();
        weightTextArea.setWidth("30%");
        tempertureTextArea.setWidth("30%");
        commentTextArea.setWidth("40%");

        interviewTextArea = new TextArea("Wywiad");
        interviewTextArea.setWidth("100%");
        interviewTextArea.setHeight("100px");

        clinicalTrialTextArea = new TextArea("Badanie kliniczne");
        clinicalTrialTextArea.setWidth("100%");
        clinicalTrialTextArea.setHeight("100px");

        diagnosisTextArea = new TextArea("Rozpoznanie");
        diagnosisTextArea.setWidth("100%");
        diagnosisTextArea.setHeight("100px");


        recommendationsTextArea = new TextArea("Zalecenia");
        recommendationsTextArea.setWidth("100%");
        recommendationsTextArea.setHeight("100px");
        treatmentLayout.add(saveChangesButton, basicInfoLayout, interviewTextArea, clinicalTrialTextArea, diagnosisTextArea, recommendationsTextArea);

        verticalLayout.setSpacing(true);

    }

    private void configureMedicationsCard() {

        medicinesGrid=  new TwinColGrid<Medicine>()
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

        Button saveChangesButton = new Button("Zapisz zmiany");
        saveChangesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Map<Long, MedicineUnit> medicineUnitMap= medicineService.getMedicineUnitsAssignToMedicineAndVisit(Pageable.unpaged(), visit.getId());
        Map<Long, BigDecimal> medicineBigDecimalMap = medicineService.getMedicineQuantityAssignToMedicineAndVisit(Pageable.unpaged(), visit.getId());

        saveChangesButton.addClickListener(event -> {
            visitService.removeVisitMedicine(visit.getId());
            for (Medicine medicine : medicinesGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                VisitMedicine visitMedicine = new VisitMedicine(visit, medicine, medicineUnitMap.get(medicine.getId()), medicineBigDecimalMap.get(medicine.getId()));
                visitService.addNewMedicineToVisit(visitMedicine);
            }
            Notification.show("Leki zapisane");
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
            comboBox.setRequired(true);
            comboBox.addValueChangeListener( x -> {
                medicineUnitMap.put(e.getId(), x.getValue());
            });
            comboBox.setItems(e.getMedicineUnits());
            comboBox.setItemLabelGenerator(MedicineUnit::getUnit);
            comboBox.setValue(medicineUnitMap.get(e.getId()));
            comboBox.setSizeFull();
            return comboBox;
        }).setHeader("Jednostka").setAutoWidth(true);;

        medicinesGrid.getSelectionGrid().addComponentColumn(e-> {
            BigDecimalField numberField = new BigDecimalField();
            numberField.setRequired(true);
            numberField.addValueChangeListener( x -> {
                medicineBigDecimalMap.put(e.getId(),  x.getValue());
            });
            numberField.setValue(medicineBigDecimalMap.get(e.getId()));
            numberField.setSizeFull();
            return numberField;
        }).setHeader("Ilość").setAutoWidth(true);

        medicinesGrid.setItems(medicineService.getMedicines(Pageable.unpaged()));
        medicinesGrid.setValue(medicineService.getMedicinesAssignToVisit(Pageable.unpaged(), visit.getId()).collect(Collectors.toSet()));


//         medicinesGrid.setItems();
        medicationsLayout.add(saveChangesButton, medicinesGrid);
//         medicationsLayout.setMaxHeight("1000px");
        medicationsLayout.setSizeFull();


    }
}
