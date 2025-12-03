package org.rzsp.notes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    /**
     * Запускает Spring приложение.
     */
    public static void main(String[] args) {
        log.debug("App is started");
        SpringApplication.run(Application.class, args);
        log.debug("App is closed");
    }

}
