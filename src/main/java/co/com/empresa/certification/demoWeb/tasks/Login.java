package co.com.empresa.certification.demoWeb.tasks;

import co.com.empresa.certification.demoWeb.models.TestData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.empresa.certification.demoWeb.userinterfaces.LoginPage.*;

public class Login implements Task {
    private final TestData testData;
    public Login(TestData testData) {
        this.testData = testData;
    }
    public static Login inThepage(TestData testData){
        return Tasks.instrumented(Login.class, testData);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.angularRequestsHaveFinished(),
                Enter.theValue(testData.getUserName()).into(USERNAME_INPUT),
                Enter.theValue(testData.getPassword()).into(PASSWORD_INPUT),
                Click.on(SIGN_IN_BUTTON)
        );
    }
}
