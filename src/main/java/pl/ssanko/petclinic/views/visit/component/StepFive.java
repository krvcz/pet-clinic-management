package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.dto.ProductDto;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.service.VisitService;

import java.math.BigDecimal;

public class StepFive extends Step{
    private final Integer ORDER = 5;

    private final Icon ICON = VaadinIcon.PIGGY_BANK_COIN.create();

    private final Span NAME = new Span("5. Rozliczenie");

    private VisitService visitService;
    private Grid<ProductDto> productSummaryGrid;

    private TextField filterTextField;

    private VerticalLayout verticalLayout;

    private Button endButton;

    private Button editButton;

    private Button backButton;

    public StepFive(VisitService visitService) {
        this.visitService = visitService;
    }

    public StepFive() {

    }
    @Override
    public void configure() {
        verticalLayout =  new VerticalLayout();
        verticalLayout.add(VisitCommonComponent.createCardsInfo(visit));

        HorizontalLayout buttonsSet = new HorizontalLayout();
        buttonsSet.setSizeFull();
        buttonsSet.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        endButton = new Button("Zakończ wizytę");
        endButton.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
        endButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);

        editButton = new Button("Edytuj wizytę");
        editButton.setIcon(VaadinIcon.EDIT.create());
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addClickListener(e -> {

                    Dialog dialog = new Dialog();

                    dialog.add("Czy jesteś pewien?");

                    Button saveButton = new Button("Tak", x -> {
                        dialog.close();
                        visit.setStatus("W trakcie");
                        visitService.saveVisit(visit.getId(), visit);
                        stepper.back();
                    });

                    Button cancelButton = new Button("Nie", x -> dialog.close());
                    dialog.getFooter().add(cancelButton);
                    dialog.getFooter().add(saveButton);

                    dialog.open();

                });

        backButton = new Button("Podgląd wizyty");
        backButton.setIcon(VaadinIcon.EDIT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        backButton.addClickListener(e -> stepper.back());

        buttonsSet.add(backButton, editButton, endButton);

        productSummaryGrid = new Grid<ProductDto>(ProductDto.class);
        productSummaryGrid.setColumns("name", "type", "unit", "quantity", "price");
        productSummaryGrid.getColumnByKey("name").setHeader("Nazwa");
        productSummaryGrid.getColumnByKey("type").setHeader("Typ");
        productSummaryGrid.getColumnByKey("unit").setHeader("Jednostka");
        productSummaryGrid.getColumnByKey("quantity").setHeader("Ilość");
        productSummaryGrid.getColumnByKey("price").setHeader("Cena jednostkowa");
        productSummaryGrid.setItems(query -> visitService.getProducts(visit.getId(), PageRequest.of(query.getPage(), query.getPageSize())));
        verticalLayout.add(buttonsSet, productSummaryGrid);

    }

    @Override
    public Integer getOrder() {
        return ORDER;
    }

    @Override
    public Icon getIcon() {
        return ICON;
    }

    @Override
    public Span getName() {
        return NAME;
    }

    @Override
    public Div getContent() {
        configure();
        return new Div(verticalLayout);
    }

}