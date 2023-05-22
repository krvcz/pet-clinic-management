package pl.ssanko.petclinic.views.medicalprocedure.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;

public class MedicalProcedureDeleteForm extends MedicalProcedureForm {

    public MedicalProcedureDeleteForm(Grid<MedicalProcedure> medicalProcedureGrid, MedicalProcedureService medicalProcedureService, MedicalProcedure medicalProcedure) {
        super(medicalProcedureGrid, medicalProcedureService, medicalProcedure);
        descriptionTextField.setReadOnly(true);
        nameTextField.setReadOnly(true);
        typeComboBox.setReadOnly(true);
        priceTextField.setReadOnly(true);

        save.setText("Usuń");
    }

    @Override
    protected void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        refreshGrid();
    }

    @Override
    protected void save() {

        medicalProcedureService.deleteMedicalProcedure(medicalProcedure);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        Notification.show("Usunięto procedure!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        refreshGrid();
    }
}
