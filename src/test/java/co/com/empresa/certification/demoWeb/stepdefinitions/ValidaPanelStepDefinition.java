package co.com.empresa.certification.demoWeb.stepdefinitions;

import co.com.empresa.certification.demoWeb.models.TestData;
import co.com.empresa.certification.demoWeb.questions.PanelTitle;
import co.com.empresa.certification.demoWeb.tasks.EnterValidationForm;
import co.com.empresa.certification.demoWeb.tasks.Login;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Open;

import static co.com.empresa.certification.demoWeb.utils.constants.Constants.APP_URL;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class ValidaPanelStepDefinition {

    @Given("julian enters the metis web app")
    public void julianEntersTheMetisWebApp() {
        theActorInTheSpotlight().wasAbleTo(Open.url(APP_URL));
    }

    @When("enter your credentials and enter the app")
    public void enterYourCredentialsAndEnterTheApp(DataTable dataTable) {

        theActorInTheSpotlight().attemptsTo(Login.inThepage(TestData.setData(dataTable).get(0)));
    }

    @When("julian enters the form validation option")
    public void julianEntersTheFormValidationOption() {
        theActorInTheSpotlight().attemptsTo(EnterValidationForm.throughTheMenu());
    }

    @Then("julian checks that the panel title is correct")
    public void julianChecksThatThePanelTitleIsCorrect(DataTable dataTable) {
        theActorInTheSpotlight().should(seeThat(PanelTitle.isVisible(TestData.setData(dataTable).get(0))));
    }

}
