package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.entity.Veterinarian;

public abstract class Step {

   protected Button selectButton;
   protected Button backButton;

   protected Div content;

   protected Veterinarian veterinarian;

   protected Customer customer;

   protected Pet pet;

   protected Stepper stepper;


   public  abstract void configure();

   public  abstract Integer getOrder();

   public  abstract Icon getIcon();

   public  abstract Span getName();

   public abstract Div getContent();

   public void setStepper(Stepper stepper) {
      this.stepper = stepper;
   }

   private void setCustomer(Customer customer) {
      this.customer = customer;
   }

   private void setVeterinarian(Veterinarian veterinarian) {
      this.veterinarian = veterinarian;
   }

   private void setPet(Pet pet) {
      this.pet = pet;
   }

   public void transferEntities(Step step) {
          setPet(step.pet);
          setCustomer(step.customer);
          setVeterinarian(step.veterinarian);
   }

}
