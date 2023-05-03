package pl.ssanko.petclinic.views.visit;

import com.mlottmann.vstepper.VStepper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.ssanko.petclinic.views.MainLayout;

import java.util.Collection;


@PageTitle("Visits")
@Route(value = "visits", layout = MainLayout.class)
@PermitAll
public class VisitView  extends VerticalLayout {


    public VisitView() {
        VStepper simpleUse = new VStepper();
//        simpleUse.addStep(new Label("Step 1"));
//        simpleUse.addStep(new Label("Step 2"));
//        simpleUse.addStep(new Label("Step 3"));

      add((Collection<Component>) simpleUse);

    }



}
