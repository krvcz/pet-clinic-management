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
import com.vaadin.flow.component.notification.Notification;
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
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.data.service.VeterinarianService;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.visit.component.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Wizyta - formularz")
@Route(value = "visits/process", layout = MainLayout.class)
@PermitAll
public class VisitProcessView extends VerticalLayout implements HasUrlParameter<Long> {

    private final VisitService visitService;
    private final MedicineService medicineService;

    private final MedicalProcedureService medicalProcedureService;
    private Visit visit;

    private Stepper stepper;
    // components for top section

    public VisitProcessView(VisitService visitService, MedicineService medicineService, MedicalProcedureService medicalProcedureService) {

        this.visitService = visitService;
        this.medicineService = medicineService;
        this.medicalProcedureService = medicalProcedureService;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        this.visit = visitService.getVisitById(aLong);
        configure();

    }

    private void configure() {
        Step stepOne = new StepOne();
        Step stepTwo = new StepTwo();
        Step stepThree = new StepThree();
        Step stepFour = new StepFour(medicineService, medicalProcedureService, visitService, visit);
        Step stepFive = new StepFive(visitService, visit);
        if (visit.getStatus().equals("W trakcie")) {
            stepper = new Stepper(stepFour.getContent(), 4);
        } else {
            stepper = new Stepper(stepFive.getContent(), 5);
        }

        stepper.addStep(stepOne);
        stepper.addStep(stepTwo);
        stepper.addStep(stepThree);
        stepper.addStep(stepFour);
        stepper.addStep(stepFive);

        add(stepper.generateComponent(), stepper.getCurrentContent());

    }



}