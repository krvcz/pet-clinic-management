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
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.provider.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
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

    // tabs and contents
    private TabSheet tabSheet;
    private VerticalLayout treatmentLayout;
    private VerticalLayout medicationsLayout;
    private VerticalLayout labTestsLayout;
    private VerticalLayout imagingLayout;
    private VerticalLayout surgeriesLayout;

    // components for treatment tab
    @PropertyId("weight")
    private TextArea weightTextArea;
    @PropertyId("temperature")
    private TextArea tempertureTextArea;
    @PropertyId("comment")
    private TextArea commentTextArea;
    @PropertyId("interview")
    private TextArea interviewTextArea;
    private TextArea medicalHistoryTextArea;
    @PropertyId("clinicalTrials")
    private TextArea clinicalTrialTextArea;
    @PropertyId("diagnosis")
    private TextArea diagnosisTextArea;
    @PropertyId("recommendations")
    private TextArea recommendationsTextArea;

    private ComboBox<MedicineUnit> comboBox;

    private NumberField numberField;

    // components for medications tab

    private TwinColGrid<Medicine> medicinesGrid;

    private TwinColGrid<MedicalProcedure> medicalProcedureGrid;

    private TwinColGrid<MedicalProcedure> specialMedicalProcedureGrid;

    private TwinColGrid<MedicalProcedure> surgeryMedicalProcedureGrid;

    private BeanValidationBinder<VisitDetail> binderVisitDetail = new BeanValidationBinder<>(VisitDetail.class);

    // component for visit history
    private Grid<Visit> visitGrid;

    private  MedicineService medicineService;

    private MedicalProcedureService medicalProcedureService;

    private VisitService visitService;

    private Button saveChangesButtonMedicationsCard;

    private Button reckoningButton;

    private Button endButton;

    private Button saveChangesButtonMedicalProceduresCard;

    private Button saveChangesButtonTreatmentCard;

    private Button saveChangesButtonImaging;

    private Button saveChangesButtonSurgery;

    private Button nextStep;

    private Map<Long, MedicineUnit> medicineUnitMap;
    private Map<Long, Double> medicineBigDecimalMap;


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
        verticalLayout.add(VisitCommonComponent.createCardsInfo(visit));
        verticalLayout.add(configureStepperButtons());
        verticalLayout.add(configureTabSheet());
        readOnlyMode(visit.getStatus());
        configureClickListeners();
        binderVisitDetail.bindInstanceFields(this);
        binderVisitDetail.setBean(visit.getVisitDetail() == null ? new VisitDetail() : visit.getVisitDetail());

    }

    private void readOnlyMode(String status) {
        if (!status.equals("W trakcie")) {
            weightTextArea.setReadOnly(true);
            tempertureTextArea.setReadOnly(true);
            commentTextArea.setReadOnly(true);
            interviewTextArea.setReadOnly(true);
            clinicalTrialTextArea.setReadOnly(true);
            diagnosisTextArea.setReadOnly(true);
            recommendationsTextArea.setReadOnly(true);
            medicalProcedureGrid.setReadOnly(true);
            medicalProcedureGrid.setEnabled(false);
            medicinesGrid.setReadOnly(true);
            medicinesGrid.setEnabled(false);
            specialMedicalProcedureGrid.setReadOnly(true);
            specialMedicalProcedureGrid.setEnabled(false);
            surgeryMedicalProcedureGrid.setReadOnly(true);
            surgeryMedicalProcedureGrid.setEnabled(false);
            saveChangesButtonMedicationsCard.setVisible(false);
            reckoningButton.setVisible(false);
            saveChangesButtonMedicalProceduresCard.setVisible(false);
            saveChangesButtonTreatmentCard.setVisible(false);
            saveChangesButtonImaging.setVisible(false);
            saveChangesButtonSurgery.setVisible(false);
            nextStep.setVisible(true);

            if (status.equals("Zakończona")) {
                endButton.setVisible(false);
            }

        }


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

    private TabSheet configureTabSheet() {
        // Create tabs and contents
        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        treatmentLayout = new VerticalLayout();
        medicationsLayout = new VerticalLayout();
        labTestsLayout = new VerticalLayout();
        imagingLayout = new VerticalLayout();
        surgeriesLayout = new VerticalLayout();
        tabSheet.add("Karta leczenia", treatmentLayout);
        tabSheet.add("Leki", medicationsLayout);
        tabSheet.add("Badania laboratoryjne", labTestsLayout);
        tabSheet.add("RTG/USG", imagingLayout);
        tabSheet.add("Zabiegi", surgeriesLayout);

        configureTreatmentCard();
        configureMedicationsCard();
        configureMedicalProceduresCard();
        configureImaging();
        configureSurgery();


        return tabSheet;
    }

    private HorizontalLayout configureStepperButtons() {
        HorizontalLayout buttonsSet = new HorizontalLayout();
        buttonsSet.setSizeFull();
        buttonsSet.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        reckoningButton = new Button("Do rozliczenia");
        reckoningButton.setIcon(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
        reckoningButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);

        endButton = new Button("Zakończ wizytę");
        endButton.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
        endButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);


        nextStep = new Button("Do rozliczenia");
        nextStep.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        nextStep.setVisible(false);

        buttonsSet.add(endButton, reckoningButton, nextStep);

        return buttonsSet;
    }

    private void configureClickListeners() {
        reckoningButton.addClickListener( e -> {

            if (binderVisitDetail.isValid()) {


                visit.setVisitDetail(binderVisitDetail.getBean());
                visitService.removeBasicVisitMedicalProcedure(visit.getId());
                for (MedicalProcedure medicalProcedure : medicalProcedureGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                    VisitMedicalProcedure visitMedicalProcedure = new VisitMedicalProcedure(visit, medicalProcedure);
                    visitService.addNewMedicalProcedureToVisit(visitMedicalProcedure);
                }
                visit.setVisitDetail(binderVisitDetail.getBean());
                visitService.addNewVisitDetails(visit.getId(), binderVisitDetail.getBean());

                List<Medicine> selectedMedicines = medicinesGrid.getSelectionGrid()
                        .getDataProvider()
                        .fetch(new Query<>())
                        .collect(Collectors.toList());


                visitService.removeVisitMedicine(visit.getId());
                for (Medicine medicine : selectedMedicines) {
                    VisitMedicine visitMedicine = new VisitMedicine(visit, medicine, medicineUnitMap.get(medicine.getId()), medicineBigDecimalMap.get(medicine.getId()));
                    visitService.addNewMedicineToVisit(visitMedicine);
                }


                visitService.removeSpecialVisitMedicalProcedure(visit.getId());
                for (MedicalProcedure medicalProcedure : specialMedicalProcedureGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                    VisitMedicalProcedure visitMedicalProcedure = new VisitMedicalProcedure(visit, medicalProcedure);
                    visitService.addNewMedicalProcedureToVisit(visitMedicalProcedure);
                }


                visitService.removeSurgeryVisitMedicalProcedure(visit.getId());
                for (MedicalProcedure medicalProcedure : surgeryMedicalProcedureGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                    VisitMedicalProcedure visitMedicalProcedure = new VisitMedicalProcedure(visit, medicalProcedure);
                    visitService.addNewMedicalProcedureToVisit(visitMedicalProcedure);
                }

                Notification.show("Szczegóły zapisane!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } else {
                Notification.show("Wypełnij wymagane pola!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

            visit.setStatus("Rozliczenie");
            visitService.saveVisit(visit.getId(), visit);
            stepper.next();

        });

        nextStep.addClickListener(e -> stepper.next());


    }

    private void configureMedicalProceduresCard() {
        medicalProcedureGrid=  new TwinColGrid<MedicalProcedure>()
                .addColumn(MedicalProcedure::getName, "Nazwa")
                .addColumn(MedicalProcedure::getDescription, "Opis")
                .withLeftColumnCaption("Dostępne badania")
                .withRightColumnCaption("Wybrane badania")
                .withoutRemoveAllButton()
                .withDragAndDropSupport()
                .withSizeFull()
                .withOrientation(TwinColGrid.Orientation.VERTICAL_REVERSE)
                .withoutAddAllButton()
                .withoutRemoveAllButton();

        saveChangesButtonMedicalProceduresCard = new Button("Zapisz zmiany");
        saveChangesButtonMedicalProceduresCard.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveChangesButtonMedicalProceduresCard.addClickListener(event -> {
            visitService.removeBasicVisitMedicalProcedure(visit.getId());
            for (MedicalProcedure medicalProcedure : medicalProcedureGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                VisitMedicalProcedure visitMedicalProcedure = new VisitMedicalProcedure(visit, medicalProcedure);
                visitService.addNewMedicalProcedureToVisit(visitMedicalProcedure);
            }
            Notification.show("Procedury zapisane!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        medicalProcedureGrid.getAvailableGrid().getColumns().get(1).setAutoWidth(true);
        medicalProcedureGrid.getSelectionGrid().getColumns().get(1).setAutoWidth(true);
        medicalProcedureGrid.getAvailableGrid().getColumns().get(0).setAutoWidth(true);
        medicalProcedureGrid.getSelectionGrid().getColumns().get(0).setAutoWidth(true);

        medicalProcedureGrid.setItems(medicalProcedureService.getBasicMedicalProcedures(Pageable.unpaged()));
        medicalProcedureGrid.setValue(medicalProcedureService.getBasicMedicalProceduresAssignToVisit(Pageable.unpaged(), visit.getId()).collect(Collectors.toSet()));

        labTestsLayout.add(saveChangesButtonMedicalProceduresCard, medicalProcedureGrid);
        labTestsLayout.setSizeFull();
    }

    private void configureTreatmentCard() {
        saveChangesButtonTreatmentCard = new Button("Zapisz zmiany");
        saveChangesButtonTreatmentCard.addClickListener(e -> {
            if (binderVisitDetail.isValid()) {
                visit.setVisitDetail(binderVisitDetail.getBean());
                visitService.addNewVisitDetails(visit.getId(), binderVisitDetail.getBean());
                Notification.show("Szczegóły wizyty zaktualizowane!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } else {
                Notification.show("Popraw wartości w polach!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        saveChangesButtonTreatmentCard.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
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
        treatmentLayout.add(saveChangesButtonTreatmentCard, basicInfoLayout, interviewTextArea, clinicalTrialTextArea, diagnosisTextArea, recommendationsTextArea);

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

        saveChangesButtonMedicationsCard = new Button("Zapisz zmiany");
        saveChangesButtonMedicationsCard.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        medicineUnitMap= medicineService.getMedicineUnitsAssignToMedicineAndVisit(Pageable.unpaged(), visit.getId());
        medicineBigDecimalMap = medicineService.getMedicineQuantityAssignToMedicineAndVisit(Pageable.unpaged(), visit.getId());

        saveChangesButtonMedicationsCard.addClickListener(event -> {

                List<Medicine> selectedMedicines = medicinesGrid.getSelectionGrid()
                        .getDataProvider()
                        .fetch(new Query<>())
                        .collect(Collectors.toList());


                visitService.removeVisitMedicine(visit.getId());
                for (Medicine medicine : selectedMedicines) {
                    VisitMedicine visitMedicine = new VisitMedicine(visit, medicine, medicineUnitMap.get(medicine.getId()), medicineBigDecimalMap.get(medicine.getId()));
                    visitService.addNewMedicineToVisit(visitMedicine);
                }
                Notification.show("Leki zapisane!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);


        });

        // Rejestracja słuchacza dla Grid'a
        medicinesGrid.getAvailableGrid().getColumns().get(2).setAutoWidth(true);
        medicinesGrid.getSelectionGrid().getColumns().get(2).setAutoWidth(true);
        medicinesGrid.getAvailableGrid().getColumns().get(1).setAutoWidth(true);
        medicinesGrid.getSelectionGrid().getColumns().get(1).setAutoWidth(true);
        medicinesGrid.getAvailableGrid().getColumns().get(0).setAutoWidth(true);
        medicinesGrid.getSelectionGrid().getColumns().get(0).setAutoWidth(true);

        medicinesGrid.getSelectionGrid().addComponentColumn(e-> {
            comboBox = new ComboBox<>();
            comboBox.setRequired(true);
            comboBox.addValueChangeListener( x -> {
                medicineUnitMap.put(e.getId(), x.getValue());
            });
            comboBox.setItems(e.getMedicineUnits());
            comboBox.setItemLabelGenerator(MedicineUnit::getUnit);
            comboBox.setValue(medicineUnitMap.get(e.getId()) == null ? e.getMedicineUnits().stream().findAny().get() : medicineUnitMap.get(e.getId()) );
            comboBox.setSizeFull();
            return comboBox;
        }).setHeader("Jednostka").setAutoWidth(true);;

        medicinesGrid.getSelectionGrid().addComponentColumn(e-> {
            numberField = new NumberField();
            numberField.setRequired(true);
            numberField.setStep(0.5);
            numberField.setMin(1);
            numberField.setStepButtonsVisible(true);
            numberField.addValueChangeListener( x -> {
                medicineBigDecimalMap.put(e.getId(),  x.getValue());
            });
            numberField.setValue(medicineBigDecimalMap.get(e.getId()) == null ? Double.valueOf("1"): medicineBigDecimalMap.get(e.getId()) );
            numberField.setSizeFull();
            numberField.addValueChangeListener(value -> {
                if (value.getValue() == null) {
                    numberField.setValue(1D);
                }

            });
            return numberField;
        }).setHeader("Ilość").setAutoWidth(true);

        medicinesGrid.setItems(medicineService.getMedicines(Pageable.unpaged()));
        medicinesGrid.setValue(medicineService.getMedicinesAssignToVisit(Pageable.unpaged(), visit.getId()).collect(Collectors.toSet()));


//         medicinesGrid.setItems();
        medicationsLayout.add(saveChangesButtonMedicationsCard, medicinesGrid);
//         medicationsLayout.setMaxHeight("1000px");
        medicationsLayout.setSizeFull();

    }

    private void configureImaging() {
        specialMedicalProcedureGrid=  new TwinColGrid<MedicalProcedure>()
                .addColumn(MedicalProcedure::getName, "Nazwa")
                .addColumn(MedicalProcedure::getDescription, "Opis")
                .withLeftColumnCaption("Dostępne procedury")
                .withRightColumnCaption("Wybrane procedury")
                .withoutRemoveAllButton()
                .withDragAndDropSupport()
                .withSizeFull()
                .withOrientation(TwinColGrid.Orientation.VERTICAL_REVERSE)
                .withoutAddAllButton()
                .withoutRemoveAllButton();

        saveChangesButtonImaging = new Button("Zapisz zmiany");
        saveChangesButtonImaging.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveChangesButtonImaging.addClickListener(event -> {
            visitService.removeSpecialVisitMedicalProcedure(visit.getId());
            for (MedicalProcedure medicalProcedure : specialMedicalProcedureGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                VisitMedicalProcedure visitMedicalProcedure = new VisitMedicalProcedure(visit, medicalProcedure);
                visitService.addNewMedicalProcedureToVisit(visitMedicalProcedure);
            }
            Notification.show("Procedury zapisane!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        specialMedicalProcedureGrid.getAvailableGrid().getColumns().get(1).setAutoWidth(true);
        specialMedicalProcedureGrid.getSelectionGrid().getColumns().get(1).setAutoWidth(true);
        specialMedicalProcedureGrid.getAvailableGrid().getColumns().get(0).setAutoWidth(true);
        specialMedicalProcedureGrid.getSelectionGrid().getColumns().get(0).setAutoWidth(true);

        specialMedicalProcedureGrid.setItems(medicalProcedureService.getSpecialMedicalProcedures(Pageable.unpaged()));
        specialMedicalProcedureGrid.setValue(medicalProcedureService.getSpecialMedicalProceduresAssignToVisit(Pageable.unpaged(), visit.getId()).collect(Collectors.toSet()));

        imagingLayout.add(saveChangesButtonImaging, specialMedicalProcedureGrid);
        imagingLayout.setSizeFull();

    }

    private void configureSurgery() {
        surgeryMedicalProcedureGrid=  new TwinColGrid<MedicalProcedure>()
                .addColumn(MedicalProcedure::getName, "Nazwa")
                .addColumn(MedicalProcedure::getDescription, "Opis")
                .withLeftColumnCaption("Dostępne zabiegi")
                .withRightColumnCaption("Wybrane zabiegi")
                .withoutRemoveAllButton()
                .withDragAndDropSupport()
                .withSizeFull()
                .withOrientation(TwinColGrid.Orientation.VERTICAL_REVERSE)
                .withoutAddAllButton()
                .withoutRemoveAllButton();

        saveChangesButtonSurgery = new Button("Zapisz zmiany");
        saveChangesButtonSurgery.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveChangesButtonSurgery.addClickListener(event -> {
            visitService.removeSurgeryVisitMedicalProcedure(visit.getId());
            for (MedicalProcedure medicalProcedure : surgeryMedicalProcedureGrid.getSelectionGrid().getDataProvider().fetch(new Query<>()).collect(Collectors.toList())) {
                VisitMedicalProcedure visitMedicalProcedure = new VisitMedicalProcedure(visit, medicalProcedure);
                visitService.addNewMedicalProcedureToVisit(visitMedicalProcedure);
            }
            Notification.show("Procedury zapisane!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        surgeryMedicalProcedureGrid.getAvailableGrid().getColumns().get(1).setAutoWidth(true);
        surgeryMedicalProcedureGrid.getSelectionGrid().getColumns().get(1).setAutoWidth(true);
        surgeryMedicalProcedureGrid.getAvailableGrid().getColumns().get(0).setAutoWidth(true);
        surgeryMedicalProcedureGrid.getSelectionGrid().getColumns().get(0).setAutoWidth(true);

        surgeryMedicalProcedureGrid.setItems(medicalProcedureService.getSurgeryMedicalProcedures(Pageable.unpaged()));
        surgeryMedicalProcedureGrid.setValue(medicalProcedureService.getSurgeryMedicalProceduresAssignToVisit(Pageable.unpaged(), visit.getId()).collect(Collectors.toSet()));

        surgeriesLayout.add(saveChangesButtonSurgery, surgeryMedicalProcedureGrid);
        surgeriesLayout.setSizeFull();

    }

}
