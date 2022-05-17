package com.david.ds.teles.seed.microservices.payment;

import com.david.ds.teles.seed.microservices.commons.i18n.AppMessage;
import com.david.ds.teles.seed.microservices.commons.i18n.DefaultAppMessages;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
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
        assertEquals("invalid credit card number: 1", appMessage.getMessage("invalid_credit_card", new String[]{"1"}));
        assertEquals("credit card expired: 1", appMessage.getMessage("expired_credit_card", new String[]{"1"}));
        assertEquals("credit card rejected: 1", appMessage.getMessage("rejected_credit_card", new String[]{"1"}));
        assertEquals("products invalid", appMessage.getMessage("invalid_products", new String[]{"1"}));
    }

    void checkPtBrMessages() {
        assertEquals("cartão de crédito inválido: 1", appMessage.getMessage("invalid_credit_card", new String[]{"1"}));
        assertEquals("cartão de crédito expirado: 1", appMessage.getMessage("expired_credit_card", new String[]{"1"}));
        assertEquals("cartão de crédito rejeitado: 1", appMessage.getMessage("rejected_credit_card", new String[]{"1"}));
        assertEquals("produtos inválidos", appMessage.getMessage("invalid_products", new String[]{"1"}));
    }
}
