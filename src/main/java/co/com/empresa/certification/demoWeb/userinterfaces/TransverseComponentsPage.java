package co.com.empresa.certification.demoWeb.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class TransverseComponentsPage {
    public static final Target FORMS_MENU = Target.the("Forms menu")
            .located(By.xpath("//*[@id=\"menu\"]/li[6]/a"));
    public static final Target SUB_MENU_VALIDATION_FORM = Target.the("Sub menu validation form")
            .located(By.xpath("//*[@id=\"menu\"]/li[6]/ul/li[2]/a"));
    public static final Target PANEL_TITLE = Target.the("Panel title")
            .located(By.xpath("//h3[contains(.,'Form Validation')]"));
}
