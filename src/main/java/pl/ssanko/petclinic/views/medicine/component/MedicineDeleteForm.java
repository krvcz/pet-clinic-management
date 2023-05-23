package pl.ssanko.petclinic.views.medicine.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.views.medicine.component.MedicineForm;

public class MedicineDeleteForm extends MedicineForm {

    public MedicineDeleteForm(Grid<Medicine> medicineGrid, MedicineService medicineService, Medicine medicine) {
        super(medicineGrid, medicineService, medicine);

        administrationRouteTextField.setReadOnly(true);
        nameTextField.setReadOnly(true);
        compositionTextField.setReadOnly(true);
        contraindicationsTextField.setReadOnly(true);
        sideEffectsTextField.setReadOnly(true);
        dosageTextField.setReadOnly(true);
        manufacturerTextField.setReadOnly(true);
        registrationNumberTextField.setReadOnly(true);
        unitTextField.setReadOnly(true);
        priceTextField.setReadOnly(true);

        save.setText("Usuń");
    }

    @Override
    protected void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
    }

    @Override
    protected void save() {

        try {
            medicineService.saveMedicine(medicine);
            Dialog dialog = (Dialog) getParent().get();
            dialog.close();
            Notification.show("Dodano lek!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

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

