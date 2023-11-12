package co.com.empresa.certification.demoWeb.utils.datadrive;

import co.com.empresa.certification.demoWeb.utils.LoggerApp;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class CustomRunner extends Runner {
    private static final Logger LOGGER = Logger.getLogger(CustomRunner.class.getName());
    private Class<CucumberWithSerenity> classValue;
    private CucumberWithSerenity cucumberWithSerenity;
    public CustomRunner(Class<CucumberWithSerenity> classValue){
        this.classValue = classValue;
        try {
            cucumberWithSerenity = new CucumberWithSerenity(classValue);
        } catch (InitializationError initializationError) {
            LOGGER.severe(LoggerApp.getStackTrace(initializationError));
        }
    }

    @Override
    public Description getDescription() {
        return cucumberWithSerenity.getDescription();
    }
    private void runAnnotatedMethods(Class<?> annotation)  {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methods = this.classValue.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    try {
                        method.invoke(null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOGGER.severe(LoggerApp.getStackTrace(e));
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            runAnnotatedMethods(BeforeSuite.class);
            cucumberWithSerenity = new CucumberWithSerenity(classValue);
        } catch (Exception e) {
            LOGGER.warning(LoggerApp.getStackTrace(e));
        }
        cucumberWithSerenity.run(notifier);
    }
}
