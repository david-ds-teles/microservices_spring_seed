package com.david.ds.teles.seed.microservices.commons.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DefaultAppMessages implements AppMessage {

	private MessageSource source;

	private Locale locale = Locale.getDefault();

	public DefaultAppMessages() {
		var resourceBundle = new ResourceBundleMessageSource();
		resourceBundle.setBasenames("lang/messages", "product_messages/messages", "payment_messages/messages");
		resourceBundle.setUseCodeAsDefaultMessage(true);
		resourceBundle.setDefaultEncoding("UTF-8");
		this.source = resourceBundle;
	}

	@Override
	public String getMessage(String key, Object[] params) {
		return source.getMessage(key, params, locale);
	}

	@Override
	public String getMessage(String key) {
		return source.getMessage(key, null, locale);
	}

	@Override
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
