package co.com.empresa.certification.demoWeb.questions;

import co.com.empresa.certification.demoWeb.models.TestData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

import static co.com.empresa.certification.demoWeb.userinterfaces.TransverseComponentsPage.PANEL_TITLE;

public class PanelTitle implements Question<Boolean> {
    private final TestData testData;

    public PanelTitle(TestData testData) {
        this.testData = testData;
    }
    public static PanelTitle isVisible(TestData testData){
        return new PanelTitle(testData);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        boolean result;
        String panelTitle = Text.of(PANEL_TITLE).answeredBy(actor).toString();
        panelTitle = panelTitle.trim();

        result = panelTitle.equals(testData.getPanelTitle());
        return result;

    }
}
