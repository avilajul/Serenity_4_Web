package co.com.empresa.certification.demoWeb.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {
    public static final Target USERNAME_INPUT = Target.the("Username input")
            .located(By.xpath("//*[@id=\"login\"]/form/input[1]"));
    public static final Target PASSWORD_INPUT = Target.the("Password input")
            .located(By.xpath("//*[@id=\"login\"]/form/input[2]"));
    public static final Target SIGN_IN_BUTTON = Target.the("Sign in button").
            located(By.xpath("//*[@id=\"login\"]/form/button"));
}
