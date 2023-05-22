package pl.ssanko.petclinic.views.medicalprocedure.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;
import pl.ssanko.petclinic.views.medicalprocedure.component.MedicalProcedureForm;

public class MedicalProcedureEditForm extends MedicalProcedureForm {
    public MedicalProcedureEditForm(Grid<MedicalProcedure> medicalProcedureGrid, MedicalProcedureService medicalProcedureService, MedicalProcedure medicalProcedure) {
        super(medicalProcedureGrid, medicalProcedureService, medicalProcedure);

        if (idTextField.getValue() == null) {
            idTextField.setVisible(true);
        } else {
            idTextField.setVisible(false);
        }

    }

    @Override
    protected void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        refreshGrid();
    }

    @Override
    protected void save() {

        try {
            medicalProcedureService.saveMedicalProcedure(medicalProcedure);
            Dialog dialog = (Dialog) getParent().get();
            dialog.close();
            Notification.show("Dodano procedurę!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        } catch (NotUniqueException ex) {
            Dialog dialog = new Dialog();
            Button okButton = new Button("Rozumiem");
            okButton.addClickListener(e -> dialog.close());
            dialog.add(ex.getMessage());
            dialog.add(okButton);
            add(dialog);
            dialog.open();

        }
        refreshGrid();

    }
}
