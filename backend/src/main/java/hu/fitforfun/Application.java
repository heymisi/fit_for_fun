package hu.fitforfun;

import hu.fitforfun.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = {"hu.fitforfun"})
public class Application {
    public static void main(final String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        System.out.println(context.getBean("emailServiceImpl"));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(name="AppProperties")
    public AppProperties getAppProperties()
    {
        return new AppProperties();
    }
}


