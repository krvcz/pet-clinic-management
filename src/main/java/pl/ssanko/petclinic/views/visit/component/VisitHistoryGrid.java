package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.ssanko.petclinic.data.dto.VisitDto;


import java.util.stream.Stream;

public class VisitHistoryGrid extends Grid<VisitDto> {

    public VisitHistoryGrid() {

        initialize();

    }

    private void initialize() {
        
        addColumn(VisitDto::getId).setHeader("Id");
        addColumn(VisitDto::getDate).setHeader("Data");
        addComponentColumn(e -> VisitCommonComponent.createStatusIcon(e.getStatus())).setHeader("Status");
        addColumn(createToggleDetailsRenderer());

        setDetailsVisibleOnClick(false);
        setItemDetailsRenderer(createVisitDetailsRenderer());

//        List<Person> people = DataService.getPeople();
//        grid.setItems(people);

        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

    }

    private Renderer<VisitDto> createToggleDetailsRenderer() {
        return LitRenderer.<VisitDto> of(
                        "<vaadin-button theme=\"tertiary\" @click=\"${handleClick}\">Pokaż więcej</vaadin-button>")
                .withFunction("handleClick",
                        visit -> this.setDetailsVisible(visit,
                                !this.isDetailsVisible(visit)));
    }

    private ComponentRenderer<VisitDetailsFormLayout, VisitDto> createVisitDetailsRenderer() {
        return new ComponentRenderer<>(VisitDetailsFormLayout::new,
                VisitDetailsFormLayout::setVisitDetails);
    }

    private static class VisitDetailsFormLayout extends FormLayout {
        private final TextField basicInfo = new TextField("Podstawowe parametry");
        private final TextArea commentField = new TextArea("Komentarz");
        private final TextArea interviewField = new TextArea("Wywiad");
        private final TextArea clinicalTrialsField = new TextArea("Badania laboratoryjne");
        private final TextArea diagnosisField = new TextArea("Diagnoza");
        private final TextArea recommendationsField = new TextArea("Rekomendacje");
        private final TextArea medicalProceduresField = new TextArea("Wykonane procedury");
        private final TextArea medicinesField = new TextArea("Zastosowane/przypisane leki");

        public VisitDetailsFormLayout() {
            Stream.of(basicInfo, commentField, interviewField, clinicalTrialsField, diagnosisField,
                    recommendationsField,medicalProceduresField, medicinesField ).forEach(field -> {
                field.setReadOnly(true);
                add(field);
            });

//            setResponsiveSteps(new ResponsiveStep("0", 3));
//            setColspan(emailField, 3);
//            setColspan(phoneField, 3);
//            setColspan(streetField, 3);
        }

        public void setVisitDetails(VisitDto visitDto) {

            basicInfo.setValue(visitDto.getBasicInfo() != null ? visitDto.getBasicInfo() : "");
            commentField.setValue(visitDto.getComment() != null ? visitDto.getComment() : "");
            interviewField.setValue(visitDto.getInterview() != null ? visitDto.getInterview() : "");
            clinicalTrialsField.setValue(visitDto.getClinicalTrails() != null ? visitDto.getClinicalTrails() : "");
            diagnosisField.setValue(visitDto.getDiagnosis() != null ? visitDto.getDiagnosis() : "");
            recommendationsField.setValue(visitDto.getRecommendations() != null ? visitDto.getRecommendations() : "");
            medicalProceduresField.setValue(visitDto.getMedicalProcedures() != null ? visitDto.getMedicalProcedures() : "");
            medicinesField.setValue(visitDto.getMedicines() != null ? visitDto.getMedicines() : "");

        }

    }



}
