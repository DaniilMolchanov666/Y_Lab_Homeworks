package com.ylab.mapper;

import com.ylab.entity.Order;
import com.ylab.entity.dto.order.request.OrderRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Маппер для перевода сущностей, связанных с заказами
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {

    /**
     * Маппинг из Order в OrderRequestDto
     * @param order - обьект типа Order
     * @return обьект типа OrderRequestDto
     */
    OrderRequestDto toOrderDto(Order order);
}
