package com.david.ds.teles.seed.microservices.utils;

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

        checkDefaultMessages();

        var locale = new Locale("pt", "BR");
        appMessage.setLocale(locale);

        checkPtBRMessages();

        appMessage.setLocale(Locale.getDefault());

        checkDefaultMessages();
    }

    void checkDefaultMessages() {

        assertEquals("The resource temp requested with value test was not find.", appMessage.getMessage("not_found", new String[]{"temp", "test"}));
        assertEquals("the provided http verb is not supported by this operation.", appMessage.getMessage("http_method_not_supported"));
        assertEquals("the parameters provided are invalid. Check it and try again", appMessage.getMessage("invalid_params"));
        assertEquals("the request is invalid. Check the parameters and try again", appMessage.getMessage("invalid_request"));
        assertEquals("the body of the request hasn't been provided", appMessage.getMessage("body_not_provided"));
        assertEquals("we had an internal problem handling your request.", appMessage.getMessage("internal_server_error"));

    }

    void checkPtBRMessages() {

        assertEquals("O recurso temp solicitado com o valor test não foi encontrado.", appMessage.getMessage("not_found", new String[]{"temp", "test"}));
        assertEquals("Verbo http não suportado para esta operação.", appMessage.getMessage("http_method_not_supported"));
        assertEquals("Os parâmetros informados são inválidos.", appMessage.getMessage("invalid_params"));
        assertEquals("Requisiçao inválida. Verfique os parâmetros e tente novamente.", appMessage.getMessage("invalid_request"));
        assertEquals("Corpo da requisição não informado.", appMessage.getMessage("body_not_provided"));
        assertEquals("Tivemos um erro interno ao receber sua requisição.", appMessage.getMessage("internal_server_error"));

    }
}
