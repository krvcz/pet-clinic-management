package pl.ssanko.petclinic.views.visit;



import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        visitGrid.setItems(query -> visitService.getSortedVisits(PageRequest.of(query.getPage(),
                query.getPageSize(), Sort.by(Sort.Direction.ASC, "status"))));



        // Przycisk wyboru
        newVisitButton = new Button("Nowa wizyta");
        newVisitButton.addClassName("green-button");
        newVisitButton.addClickListener(event -> {
            UI.getCurrent().navigate(VisitProcessView.class);
        });
        add(newVisitButton, visitGrid);


    }
}



