package pl.ssanko.petclinic.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.io.ByteArrayInputStream;
import java.util.Optional;
import org.vaadin.lineawesome.LineAwesomeIcon;
import pl.ssanko.petclinic.components.appnav.AppNav;
import pl.ssanko.petclinic.components.appnav.AppNavItem;
import pl.ssanko.petclinic.data.entity.User;
import pl.ssanko.petclinic.security.AuthenticatedUser;
import pl.ssanko.petclinic.views.event.EventView;
import pl.ssanko.petclinic.views.customer.CustomerView;
import pl.ssanko.petclinic.views.home.MainPage;
import pl.ssanko.petclinic.views.medicalprocedure.MedicalProcedureView;
import pl.ssanko.petclinic.views.medicine.MedicineView;
import pl.ssanko.petclinic.views.visit.VisitHistoryView;
import pl.ssanko.petclinic.views.visit.VisitPreProcessView;
import pl.ssanko.petclinic.views.visit.VisitView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout  {

    private H2 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Pet Clinic");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        appName.add(LineAwesomeIcon.PAW_SOLID.create());
        Header header = new Header(appName);


        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        if (accessChecker.hasAccess(MainPage.class)) {
            nav.addItem(new AppNavItem("Strona główna", MainPage.class,
                    LineAwesomeIcon.HOME_SOLID.create()));
        }

        if (accessChecker.hasAccess(EventView.class)) {
            nav.addItem(new AppNavItem("Kalendarz", EventView.class,
                    LineAwesomeIcon.CALENDAR.create()));
        }

        if (accessChecker.hasAccess(VisitView.class)) {
            AppNavItem addItem = new AppNavItem("Wizyty", VisitView.class, LineAwesomeIcon.CALENDAR_PLUS.create());
            addItem.addItem(new AppNavItem("Nowa wizyta", VisitPreProcessView.class, LineAwesomeIcon.PLUS_SOLID.create()),
                    new AppNavItem("Historia", VisitHistoryView.class, LineAwesomeIcon.HISTORY_SOLID.create()));
            nav.addItem(addItem);
        }

        if (accessChecker.hasAccess(EventView.class)) {
            AppNavItem addItem = new AppNavItem("Klienci", CustomerView.class, LineAwesomeIcon.ADDRESS_BOOK.create());
            nav.addItem(addItem);
        }


        if (accessChecker.hasAccess(MedicineView.class)) {
            AppNavItem addItem = new AppNavItem("Leki", MedicineView.class, LineAwesomeIcon.CAPSULES_SOLID.create());
            nav.addItem(addItem);
        }

        if (accessChecker.hasAccess(MedicalProcedureView.class)) {
            AppNavItem addItem = new AppNavItem("Usługi", MedicalProcedureView.class, LineAwesomeIcon.STETHOSCOPE_SOLID.create());

            nav.addItem(addItem);
        }




        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(user.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
