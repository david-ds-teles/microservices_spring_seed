package com.david.ds.teles.seed.microservices.commons.i18n;

import java.util.Locale;

public interface AppMessage {
	public String getMessage(String key, Object[] params);

	public String getMessage(String key);

	public void setLocale(Locale locale);
}
