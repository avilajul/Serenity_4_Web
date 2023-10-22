package co.com.empresa.certification.demoWeb.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.empresa.certification.demoWeb.userinterfaces.TransverseComponentsPage.FORMS_MENU;
import static co.com.empresa.certification.demoWeb.userinterfaces.TransverseComponentsPage.SUB_MENU_VALIDATION_FORM;

public class EnterValidationForm implements Task {
    public static EnterValidationForm throughTheMenu(){
        return Tasks.instrumented(EnterValidationForm.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.angularRequestsHaveFinished(),
                Click.on(FORMS_MENU),
                Click.on(SUB_MENU_VALIDATION_FORM)
        );
    }
}
