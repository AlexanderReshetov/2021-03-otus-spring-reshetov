package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.LocaleConfig;

import java.util.Locale;

@Service
public class MessageSourceServiceImpl implements MessageSourceService{
    private final MessageSource messageSource;
    private final Locale locale;

    @Autowired
    MessageSourceServiceImpl(MessageSource messageSource, LocaleConfig localeConfig) {
        this.messageSource = messageSource;
        this.locale = new Locale(localeConfig.getName());
    }

    public String getMessage(String name, Object[] args) {
        return messageSource.getMessage(name, args, locale);
    }
}
