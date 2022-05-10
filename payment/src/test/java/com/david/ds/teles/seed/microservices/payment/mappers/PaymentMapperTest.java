package com.david.ds.teles.seed.microservices.payment.mappers;

import com.david.ds.teles.seed.microservices.payment.data.entities.PaymentEntity;
import com.david.ds.teles.seed.microservices.payment.dto.PaymentDTO;
import com.david.ds.teles.seed.microservices.payment.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentMapperTest {
    private PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void mapperTests() {

        assertNotNull(mapper);

        PaymentDTO dto = new PaymentDTO(1111, BigDecimal.TEN, PaymentStatus.PAID, List.of("1"));

        PaymentEntity entity = mapper.toEntity(dto);

        assertEquals(dto.cardNumber(), entity.getCardNumber());
        assertEquals(dto.value(), entity.getValue());
        assertEquals(dto.status(), entity.getStatus());

        entity.setId(1l);
        PaymentDTO dtoCheck = mapper.toDTO(entity);

        assertEquals(dtoCheck.id(), entity.getId());
        assertEquals(dtoCheck.cardNumber(), entity.getCardNumber());
        assertEquals(dtoCheck.value(), entity.getValue());
        assertEquals(dtoCheck.status(), entity.getStatus());
    }
}
