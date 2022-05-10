package com.david.ds.teles.seed.microservices.payment.mappers;

import com.david.ds.teles.seed.microservices.payment.data.entities.PaymentEntity;
import com.david.ds.teles.seed.microservices.payment.dto.PaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "ducts", ignore = true)
    PaymentDTO toDTO(PaymentEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    PaymentEntity toEntity(PaymentDTO dto);
}
