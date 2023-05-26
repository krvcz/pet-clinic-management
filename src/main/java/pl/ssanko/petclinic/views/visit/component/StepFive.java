package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.dto.ProductDto;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.visit.VisitView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StepFive extends Step {
    private final Integer ORDER = 5;

    private final Icon ICON = VaadinIcon.PIGGY_BANK_COIN.create();

    private final Span NAME = new Span("5. Rozliczenie");

    private H2 priceLabel;

    private VisitService visitService;
    private Grid<ProductDto> productSummaryGrid;

    private TextField filterTextField;

    private VerticalLayout verticalLayout;

    private Button endButton;

    private Button backButton;

    public StepFive(VisitService visitService, Visit visit) {
        this.visitService = visitService;
        this.visit = visit;
    }

    public StepFive() {

    }

    @Override
    public void configure() {
        verticalLayout = new VerticalLayout();
        verticalLayout.add(VisitCommonComponent.createCardsInfo(visit));

        HorizontalLayout buttonsSet = new HorizontalLayout();
        buttonsSet.setSizeFull();
        buttonsSet.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        endButton = new Button("Zakończ wizytę");
        endButton.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
        endButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);

        endButton.addClickListener(e -> {
            Dialog dialog = new Dialog();

            dialog.setHeaderTitle("Czy jesteś pewien?");
            dialog.add("Zakończonej wizyty nie będzie można edytować");

            Button saveButton = new Button("Tak", x -> {
                dialog.close();
                visitService.closeVisit(visit);
                endButton.getUI().ifPresent(ui -> ui.navigate(
                        VisitView.class));
                Notification.show("Wizyta zakończona!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            });
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            saveButton.getStyle().set("margin-right", "auto");
            dialog.getFooter().add(saveButton);


            Button cancelButton = new Button("Nie", x -> dialog.close());
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            dialog.getFooter().add(cancelButton);


            dialog.open();

        });


        backButton = new Button("Podgląd wizyty");
        backButton.setIcon(VaadinIcon.ARROW_CIRCLE_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        backButton.addClickListener(e -> stepper.back());

        buttonsSet.add(backButton, endButton);

        productSummaryGrid = new Grid<ProductDto>(ProductDto.class);
        productSummaryGrid.setColumns("name", "type", "unit", "quantity", "price");
        productSummaryGrid.getColumnByKey("name").setHeader("Nazwa");
        productSummaryGrid.getColumnByKey("type").setHeader("Typ");
        productSummaryGrid.getColumnByKey("unit").setHeader("Jednostka");
        productSummaryGrid.getColumnByKey("quantity").setHeader("Ilość");
        productSummaryGrid.getColumnByKey("price").setHeader("Cena jednostkowa");
        productSummaryGrid.addComponentColumn(this::configureColumnComponent).setHeader("Cena")
                    .setAutoWidth(true);


        productSummaryGrid.setItems(query -> visitService.getProducts(visit.getId(), PageRequest.of(query.getPage(), query.getPageSize())));

        BigDecimal summaryPrice = productSummaryGrid.getDataProvider().fetch(new Query<>())
                .map(row -> row.getQuantity() != null ? row.getPrice().multiply(BigDecimal.valueOf(row.getQuantity())) : row.getPrice())
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).setScale(2, RoundingMode.HALF_EVEN);

        priceLabel = new H2("Cena całkowita: " + summaryPrice + " PLN");
        verticalLayout.add(buttonsSet, priceLabel, productSummaryGrid);
        readOnlyMode(visit.getStatus());

    }

    private HorizontalLayout configureColumnComponent(ProductDto productDto) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        BigDecimalField priceTextField = new BigDecimalField();
        priceTextField.setSuffixComponent(new Span("PLN"));
        priceTextField.setReadOnly(true);
        priceTextField.setValue(productDto.getQuantity() != null ?
                productDto.getPrice().multiply(BigDecimal.valueOf(productDto.getQuantity())).setScale(2, RoundingMode.HALF_EVEN) :
                productDto.getPrice().setScale(2, RoundingMode.HALF_EVEN));
        horizontalLayout.add(priceTextField);
        return horizontalLayout;
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

    private void readOnlyMode(String status) {
            if (status.equals("Zakończona")) {
                endButton.setVisible(false);
            }


        }

}