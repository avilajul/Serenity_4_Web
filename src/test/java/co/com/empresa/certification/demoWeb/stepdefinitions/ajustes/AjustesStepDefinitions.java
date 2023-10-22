package co.com.empresa.certification.demoWeb.stepdefinitions.ajustes;


import co.com.empresa.certification.demoWeb.utils.KillBrowser;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.SerenityWebdriverManager;

import java.io.IOException;
import java.util.List;

public class AjustesStepDefinitions {

    @Before
    public void setUpStage(){
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("Julian");

    }

    @After
    public static void CloseDriver() throws IOException, InterruptedException {
        SerenityWebdriverManager.inThisTestThread().getCurrentDriver().quit();
        KillBrowser.processes(List.of((SerenityWebdriverManager.inThisTestThread().getCurrentDriverName()).split(":")).get(0));
    }

}
