package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class VisitCommonComponent {


    public static HorizontalLayout createStatusIcon(String status) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Span span;
        Icon icon;

        if (status.equals("W trakcie")) {
            icon = VaadinIcon.DOT_CIRCLE.create();
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
