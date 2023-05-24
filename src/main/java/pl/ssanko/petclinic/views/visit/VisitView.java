package pl.ssanko.petclinic.views.visit;



import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.visit.component.VisitCommonComponent;


@PageTitle("Wizyty")
@Route(value = "visits", layout = MainLayout.class)
@PermitAll
public class VisitView extends VerticalLayout {

    private Grid<Visit> visitGrid;
    private Button newVisitButton;
    private VisitService visitService;


    public VisitView(VisitService visitService) {
        this.visitService = visitService;

        // Grid z klientami
        visitGrid = new Grid<>(Visit.class);
        visitGrid.removeAllColumns();
        visitGrid.addColumn(Visit::getId).setHeader("Id");
        visitGrid.addColumn(Visit::getDate).setHeader("Data utworzenia/modyfikacji").setAutoWidth(true).setFlexGrow(1);
        visitGrid.addColumn(Visit::getPet).setHeader("Zwierzak").setAutoWidth(true);
        visitGrid.addColumn(Visit::getVeterinarian).setHeader("Weterynarz").setAutoWidth(true);;
        visitGrid.addComponentColumn(visit -> VisitCommonComponent.createStatusIcon(visit.getStatus())).setHeader("Status");
        visitGrid.addComponentColumn(this::createVisitButton).setHeader("Zarządzaj");

        visitGrid.setItems(query -> visitService.getSortedVisits(PageRequest.of(query.getPage(),
                query.getPageSize(), Sort.by(Sort.Direction.ASC, "status"))));

        visitGrid.setSelectionMode(Grid.SelectionMode.NONE);



        // Przycisk wyboru
        newVisitButton = new Button("Nowa wizyta");
        newVisitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        newVisitButton.addClickListener(event -> {
            UI.getCurrent().navigate(VisitPreProcessView.class);
        });
        add(newVisitButton, visitGrid);


    }

    private HorizontalLayout createVisitButton(Visit visit) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        if (visit.getStatus().equals("W trakcie")) {
            Button button = new Button("Kontynuuj");
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            horizontalLayout.add(button);
            button.addClickListener(e -> button.getUI().ifPresent(ui -> ui.navigate(
                    VisitProcessView.class, visit.getId())));
        } else if ((visit.getStatus().equals("Rozliczenie"))) {
            Button button = new Button("Rozlicz");
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            horizontalLayout.add(button);
            button.addClickListener(e -> button.getUI().ifPresent(ui -> ui.navigate(
                    VisitProcessView.class, visit.getId())));
        } else  {
            Button button = new Button("Podgląd");
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            horizontalLayout.add(button);
            button.addClickListener(e -> button.getUI().ifPresent(ui -> ui.navigate(
                    VisitProcessView.class, visit.getId())));
        }

        return horizontalLayout;
    }
}



