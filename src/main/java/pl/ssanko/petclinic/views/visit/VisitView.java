package pl.ssanko.petclinic.views.visit;



import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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


@PageTitle("Visits")
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
        visitGrid.addColumn(Visit::getPet).setHeader("Zwierzak").setAutoWidth(true);
        visitGrid.addColumn(Visit::getVeterinarian).setHeader("Weterynarz").setAutoWidth(true);;
        visitGrid.addComponentColumn(visit -> createStatusIcon(visit.getStatus())).setHeader("Status");
        visitGrid.addComponentColumn(visit -> createVisitButton(visit.getStatus())).setHeader("Zarządzaj");

        visitGrid.setItems(query -> visitService.getSortedVisits(PageRequest.of(query.getPage(),
                query.getPageSize(), Sort.by(Sort.Direction.ASC, "status"))));

        visitGrid.setSelectionMode(Grid.SelectionMode.NONE);



        // Przycisk wyboru
        newVisitButton = new Button("Nowa wizyta");
        newVisitButton.addClassName("green-button");
        newVisitButton.addClickListener(event -> {
            UI.getCurrent().navigate(VisitPreProcessView.class);
        });
        add(newVisitButton, visitGrid);


    }

    private HorizontalLayout createVisitButton(String status) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        if (status.equals("W trakcie")) {
            Button button = new Button("Kontynuuj");
            horizontalLayout.add(button);
        } else if ((status.equals("Rozliczenie"))) {
            Button button = new Button("Rozlicz");
            horizontalLayout.add(button);
        } else  {
            Button button = new Button("Podgląd");
            horizontalLayout.add(button);
        }

        return horizontalLayout;
    }

    private HorizontalLayout createStatusIcon(String status) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Span span;
        Icon icon;

        if (status.equals("W trakcie")) {
            icon = VaadinIcon.CHECK.create();
            span = new Span();
            span.add(icon);
            span.add("W trakcie");
            span.getElement().getThemeList().add("badge success");
            horizontalLayout.add(span);

        } else if ((status.equals("Rozliczenie"))) {
            icon = VaadinIcon.INVOICE.create();
            span = new Span();
            span.add(icon);
            span.add("Rozliczenie");
            span.getElement().getThemeList().add("badge warning");
            horizontalLayout.add(icon, span);
        } else  {
            icon = VaadinIcon.CLOSE_SMALL.create();
            span = new Span();
            span.add(icon);
            span.add("Zakończona");
            span.getElement().getThemeList().add("badge error");
            horizontalLayout.add(icon, span);
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return horizontalLayout;
    }
}



