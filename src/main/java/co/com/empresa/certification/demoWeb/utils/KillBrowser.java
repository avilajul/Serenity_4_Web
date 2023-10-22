package co.com.empresa.certification.demoWeb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class KillBrowser {
    private KillBrowser() {
        throw new IllegalStateException("Utility class");
    }

    static final Logger logger = LoggerFactory.getLogger(KillBrowser.class);

    public static void processes(String browser) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/IM", browser + ".exe");
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            logger.debug("Procesos del browser cerrados exitosamente");
        } else {
            logger.debug("No existen procesos del browser o no fue posible cerrarlos");
        }
    }
}
