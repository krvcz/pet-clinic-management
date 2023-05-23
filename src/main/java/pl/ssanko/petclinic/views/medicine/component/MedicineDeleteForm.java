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

            medicineService.deleteMedicine(medicine);
            Dialog dialog = (Dialog) getParent().get();
            dialog.close();
            Notification.show("Usunięto lek!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);


        refreshGrid();

    }
}

