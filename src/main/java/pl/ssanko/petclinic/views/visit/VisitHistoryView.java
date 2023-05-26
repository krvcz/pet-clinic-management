package pl.ssanko.petclinic.views.visit;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.visit.component.VisitCommonComponent;

import java.time.format.DateTimeFormatter;

@PageTitle("Wizyty")
@Route(value = "visits/history", layout = MainLayout.class)
@PermitAll
public class VisitHistoryView extends VerticalLayout {

    private Grid<Visit> visitGrid;
    private VisitService visitService;


    public VisitHistoryView(VisitService visitService) {
        this.visitService = visitService;

        // Grid z klientami
        visitGrid = new Grid<>(Visit.class);
        visitGrid.removeAllColumns();
        visitGrid.addColumn(Visit::getId).setHeader("Id");
        visitGrid.addColumn(row -> row.getDate().format((DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))).setHeader("Data utworzenia/modyfikacji").setAutoWidth(true).setFlexGrow(1);
        visitGrid.addColumn(Visit::getPet).setHeader("Zwierzak").setAutoWidth(true);
        visitGrid.addColumn(Visit::getVeterinarian).setHeader("Weterynarz").setAutoWidth(true);;
        visitGrid.addComponentColumn(visit -> VisitCommonComponent.createStatusIcon(visit.getStatus())).setHeader("Status");
        visitGrid.addComponentColumn(this::createVisitButton).setHeader("Podgląd");

        visitGrid.setItems(query -> visitService.getSortedByEndedDateVisits(PageRequest.of(query.getPage(), query.getPageSize())));

        visitGrid.setSelectionMode(Grid.SelectionMode.NONE);

        visitGrid.setHeight("800px");

        add(visitGrid);




    }

    private HorizontalLayout createVisitButton(Visit visit) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
            Button button = new Button("Podgląd");
            button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            horizontalLayout.add(button);
            button.addClickListener(e -> button.getUI().ifPresent(ui -> ui.navigate(
                    VisitProcessView.class, visit.getId())));


        return horizontalLayout;
    }
}
