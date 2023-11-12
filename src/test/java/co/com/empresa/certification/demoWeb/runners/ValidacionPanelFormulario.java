package co.com.empresa.certification.demoWeb.runners;

import co.com.empresa.certification.demoWeb.utils.datadrive.BeforeSuite;
import co.com.empresa.certification.demoWeb.utils.datadrive.CustomRunner;
import co.com.empresa.certification.demoWeb.utils.datadrive.DataToFeature;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(CustomRunner.class)
@CucumberOptions(
        features = "src/test/resources/features/validacion_panel_formulario.feature",
        glue = "co.com.empresa.certification.demoWeb.stepdefinitions",
        snippets = SnippetType.CAMELCASE
)
public class ValidacionPanelFormulario {
    private ValidacionPanelFormulario() {
        throw new IllegalStateException("Utility class");
    }
    @BeforeSuite
    public static void test() throws InvalidFormatException, IOException{
        DataToFeature.overrideFeautreFiles("src/test/resources/features/validacion_panel_formulario.feature");
    }
}