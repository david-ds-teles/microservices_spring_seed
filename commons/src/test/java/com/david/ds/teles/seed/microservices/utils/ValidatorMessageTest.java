package com.david.ds.teles.seed.microservices.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorMessageTest {

    Validator validator;

    public ValidatorMessageTest() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

    }

    @Test
    void check_validate_messages() {
        Locale.setDefault(new Locale("pt", "BR"));

        var entity = new TestEntity("");
        Set<ConstraintViolation<TestEntity>> violations = this.validator.validate(entity);

        for (ConstraintViolation<?> violation : violations) {
            String field = violation.getPropertyPath().toString();
            String temp = violation.getMessage();
            System.out.println(field + ":" + temp);
            assertEquals("name:deve ser informado. Valor informado \"\"", field + ":" + temp);
        }
    }


    @AllArgsConstructor
    @Data
    static class TestEntity {

        @NotBlank
        private String name;

    }
}
