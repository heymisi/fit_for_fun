package hu.fitforfun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"hu.fitforfun"})
public class Application {
    /**
     * This is where the application starts.
     *
     * @param args is the program input
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


