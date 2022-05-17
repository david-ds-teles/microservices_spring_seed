package com.david.ds.teles.seed.microservices.product;

import com.david.ds.teles.seed.microservices.commons.i18n.AppMessage;
import com.david.ds.teles.seed.microservices.commons.i18n.DefaultAppMessages;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageBundleTest {

    private AppMessage appMessage;

    public MessageBundleTest() {
        appMessage = new DefaultAppMessages();
    }

    @Test
    void check_messages() {
        var locale = new Locale("pt", "BR");

        checkDefaultMessages();

        appMessage.setLocale(locale);

        checkPtBrMessages();

        appMessage.setLocale(Locale.getDefault());

        checkDefaultMessages();
    }

    void checkDefaultMessages() {
        assertEquals("the provided id 1 is duplicated", appMessage.getMessage("duplicated_product_id", new String[]{"1"}));
        assertEquals("no product found for id: 1", appMessage.getMessage("product_not_found", new String[]{"1"}));
    }

    void checkPtBrMessages() {
        assertEquals("o produto com id 1 já existe", appMessage.getMessage("duplicated_product_id", new String[]{"1"}));
        assertEquals("nenhum produto encontrado para id: 1", appMessage.getMessage("product_not_found", new String[]{"1"}));
    }
}
