package com.ylab.mapper;

import com.ylab.entity.Order;
import com.ylab.entity.dto.order.OrderForUpdateStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {

    Order toOrder(OrderForUpdateStatusDto orderDto);

    OrderForUpdateStatusDto toOrderDto(Order order);
}
