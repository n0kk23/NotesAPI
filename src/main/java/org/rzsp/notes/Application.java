package org.rzsp.notes;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения.
 */
@Log4j2
@SpringBootApplication
public class Application {

    /**
     * Запускает Spring приложение.
     * @param args - аргументы вводимые при запуске
     */
    public static void main(String[] args) {
        log.debug("App is started");
        SpringApplication.run(Application.class, args);
    }

}
