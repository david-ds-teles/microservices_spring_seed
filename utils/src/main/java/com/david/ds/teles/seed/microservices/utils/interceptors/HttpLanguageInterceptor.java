package com.david.ds.teles.seed.microservices.utils.interceptors;

import com.david.ds.teles.seed.microservices.utils.i18n.AppMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class HttpLanguageInterceptor implements HandlerInterceptor {

    @Autowired
    private AppMessage appMessage;

    private String currentLang = Locale.getDefault().toLanguageTag();

    private SessionLocaleResolver localeResolver = new SessionLocaleResolver();

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String lang = request.getHeader("accept-language");

        if (lang == null) {
            currentLang = Locale.getDefault().toLanguageTag();
            appMessage.setLocale(Locale.getDefault());
            localeResolver.setDefaultLocale(Locale.getDefault());
            return true;
        }

        if (currentLang.equalsIgnoreCase(lang))
            return true;

        Locale locale;
        switch (lang.toLowerCase()) {

            case "pt-br":
                locale = new Locale("pt", "BR");
                break;
            default:
                locale = Locale.getDefault();
        }

        appMessage.setLocale(locale);
        localeResolver.setDefaultLocale(locale);
        currentLang = lang;
        return true;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return this.localeResolver;
    }
}
