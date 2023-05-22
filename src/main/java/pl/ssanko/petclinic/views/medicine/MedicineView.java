package pl.ssanko.petclinic.views.medicine;





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
import org.vaadin.crudui.crud.impl.GridCrud;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.visit.VisitPreProcessView;
import pl.ssanko.petclinic.views.visit.component.VisitCommonComponent;


@PageTitle("Leki")
@Route(value = "medicines", layout = MainLayout.class)
@PermitAll
public class MedicineView extends VerticalLayout {

    private GridCrud<Medicine> medicineGrid;
    private MedicineService medicineService;


    public MedicineView(MedicineService medicineService) {
        this.medicineService = medicineService;

        // Grid z klientami
        medicineGrid = new GridCrud<>(Medicine.class);

//        medicineGrid.removeAllColumns();
//        medicineGrid.addColumn(Visit::getId).setHeader("Id");
//        medicineGrid.addColumn(Visit::getPet).setHeader("Zwierzak").setAutoWidth(true);
//        medicineGrid.addColumn(Visit::getVeterinarian).setHeader("Weterynarz").setAutoWidth(true);;
//        medicineGrid.addComponentColumn(visit -> VisitCommonComponent.createStatusIcon(visit.getStatus())).setHeader("Status");
//        medicineGrid.addComponentColumn(visit -> createVisitButton(visit.getStatus())).setHeader("Zarządzaj");
//
//        medicineGrid.setItems(query -> visitService.getSortedVisits(PageRequest.of(query.getPage(),
//                query.getPageSize(), Sort.by(Sort.Direction.ASC, "status"))));
//


        add(medicineGrid);


    }

}



