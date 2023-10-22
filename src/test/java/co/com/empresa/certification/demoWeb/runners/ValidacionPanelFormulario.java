package co.com.empresa.certification.demoWeb.runners;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/validacion_panel_formulario.feature",
        glue = "co.com.empresa.certification.demoWeb.stepdefinitions",
        snippets = SnippetType.CAMELCASE
)
public class ValidacionPanelFormulario {
}