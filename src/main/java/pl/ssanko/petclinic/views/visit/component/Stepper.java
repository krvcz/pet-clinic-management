package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Stepper {

    private List<Step> steps;

    private Integer stepPos = 0;

    private Tabs tabs;

    private Div currentContent;

    public Stepper(Div currentContent) {
        this.currentContent = currentContent;
        currentContent.setWidthFull();
    }

    public Stepper(Div currentContent, Integer startPos) {
        this(currentContent);
        this.stepPos = startPos - 1;
    }

    public Step addStep(Step step){
        if (steps == null) {
            this.steps = new LinkedList<>();
        }
        steps.add(step);
        step.setStepper(this);
        steps = steps.stream()
                .sorted((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()))
                .collect(Collectors.toList());

        return step;
    }


    public HorizontalLayout generateComponent() {
        //utworzenie kontenera na tabsy
        HorizontalLayout tabsContainer = new HorizontalLayout();
        tabsContainer.setWidthFull();
        tabsContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        tabs = new Tabs();

        //tworzenie zakładek
        for (Step step: steps) {
            Tab tab = new Tab(step.getIcon(), step.getName());
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
            tabs.add(tab);
        }



        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        tabs.setSelectedTab(tabs.getTabAt(stepPos));
        tabsContainer.add(tabs);

        return tabsContainer;
    }

    public boolean hasNext() {
        return steps.size() > stepPos + 1;
    }

    public boolean hasPrevious() {
        return stepPos - 1 >= 0;
    }

    public Step next() {
        tabs.getTabAt(stepPos).setSelected(false);
        Step oldStep = steps.get(stepPos);

        if (hasNext()) {
            stepPos = stepPos + 1;
        } else {
            stepPos = 0;
        }
        tabs.getTabAt(stepPos).setSelected(true);

        Step newStep = steps.get(stepPos);

        newStep.transferEntities(oldStep);

        setCurrentContent(newStep.getContent());


        return steps.get(stepPos);
    }

    public Step back() {
        tabs.getTabAt(stepPos).setSelected(false);
        Step oldStep = steps.get(stepPos);

        if (hasPrevious()) {
            stepPos = stepPos - 1;
        } else {
            stepPos = steps.size() - 1;
        }
        tabs.getTabAt(stepPos).setSelected(true);

        Step newStep = steps.get(stepPos);

        newStep.transferEntities(oldStep);

        setCurrentContent(newStep.getContent());

        return steps.get(stepPos);
    }

    public Step getCurrentStep() {
        return this.steps.get(stepPos);
    }

    private void clearContent() {
        this.currentContent.removeAll();
    }

    public void setCurrentContent(Div currentContent){
        clearContent();
        this.currentContent.add(currentContent);
    }

    public void setStepPos(Integer stepPos) {
        this.stepPos = stepPos - 1;

    }

}
