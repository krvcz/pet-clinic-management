package pl.ssanko.petclinic.views.visit;

import ch.qos.logback.classic.pattern.DateConverter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.time.CalendarUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.entity.Veterinarian;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.service.VeterinarianService;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;

import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@PageTitle("Visits")
@Route(value = "visits/process", layout = MainLayout.class)
@PermitAll
public class VisitProcessView extends VerticalLayout implements HasUrlParameter<Long> {

    private VisitService visitService;
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
    private TextArea symptomsTextArea;
    private TextArea medicalHistoryTextArea;
    private TextArea physicalExamTextArea;
    private TextArea diagnosisTextArea;
    private TextArea treatmentPlanTextArea;

    // components for medications tab
//    private Grid<Medication> medicationGrid;
    private Button addMedicationButton;

    // components for lab tests tab
//    private Grid<LabTest> labTestGrid;
    private Button addLabTestButton;

    // components for imaging tab
//    private Grid<ImagingResult> imagingGrid;
    private Button addImagingResultButton;

    // component for visit history
    private Grid<Visit> visitGrid;

    public VisitProcessView(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        this.visit = visitService.getVisitById(aLong);
        configureCardsInfo();
        configureTabSheet();
        configureVisitCard();
        configure();
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
        //         Initialize layout
        setMargin(true);
        setSpacing(true);

    }

    private void configureVisitCard() {
        symptomsTextArea = new TextArea("Wywiad");
        symptomsTextArea.setWidth("100%");
        symptomsTextArea.setHeight("100px");
//        symptomsTextArea.setValue(visit.getSymptoms());


        physicalExamTextArea = new TextArea("Badanie kliniczne");
        physicalExamTextArea.setWidth("100%");
        physicalExamTextArea.setHeight("100px");
//        physicalExamTextArea.setValue(visit.getPhysicalExam());

        diagnosisTextArea = new TextArea("Rozpoznanie");
        diagnosisTextArea.setWidth("100%");
        diagnosisTextArea.setHeight("100px");
//        diagnosisTextArea.setValue(visit.getDiagnosis());

        treatmentPlanTextArea = new TextArea("Zalecenia");
        treatmentPlanTextArea.setWidth("100%");
        treatmentPlanTextArea.setHeight("100px");
//        treatmentPlanTextArea.setValue(visit.getTreatmentPlan());

        add(symptomsTextArea, physicalExamTextArea, diagnosisTextArea, treatmentPlanTextArea);
        setSpacing(true);
    }

}