package es.danpintas.talaialde;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class VaadinWebApplication {

    @Value("${app.language}")
    private String localeLanguage;
    @Value("${app.region}")
    private String localeRegion;

    public static void main(String[] args) {
		SpringApplication.run(VaadinWebApplication.class, args);
	}
    
    @Bean
    public Locale appLocale() {
        return new Locale(localeLanguage, localeRegion);
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(appLocale());
        return slr;
    }
    
}
