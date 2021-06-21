package ru.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "locale")
@Component
public class LocaleConfig {
    private String name;
    private Locale locale;

    public LocaleConfig() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.locale = new Locale(name);
    }

    public Locale getLocale() {
        return locale;
    }
}
