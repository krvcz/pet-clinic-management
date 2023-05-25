package pl.ssanko.petclinic.views.home;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.ssanko.petclinic.data.service.StatsService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.home.component.StatisticsPanel;

@PageTitle("Pet Clinic")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class MainPage extends VerticalLayout {

    private final StatsService service;


    public MainPage(StatsService service) {
        this.service = service;
//        setJustifyContentMode(JustifyContentMode.EVENLY);
        setWidthFull();
        add(new StatisticsPanel(service.getSystemStats()));
    }
}
