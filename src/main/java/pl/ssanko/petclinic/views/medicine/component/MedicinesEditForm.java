package pl.ssanko.petclinic.views.medicine.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.service.MedicalProcedureService;
import pl.ssanko.petclinic.data.service.MedicineService;
import pl.ssanko.petclinic.views.medicalprocedure.component.MedicalProcedureForm;

public class MedicinesEditForm extends MedicineForm {
    public MedicinesEditForm(Grid<Medicine> medicineGrid, MedicineService medicineService, Medicine medicine) {
        super(medicineGrid, medicineService, medicine);


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

