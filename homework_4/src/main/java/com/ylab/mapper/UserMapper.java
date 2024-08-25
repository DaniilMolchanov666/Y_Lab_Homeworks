package com.ylab.mapper;

import com.ylab.entity.User;
import com.ylab.entity.dto.UserDto;
import com.ylab.entity.dto.UserForShowDto;
import com.ylab.entity.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Маппер для перевода сущностей, связанных с пользователями
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User toUSer(UserDto userDto);

    User toUSer(UserForShowDto userDto);

    UserDto toUserDto(User user);

    UserForShowDto toUserForShowDto(User user);

    void updateOwnProfile(UserUpdateDto userDto, @MappingTarget User user);

    void updateRole(UserForShowDto userDto, @MappingTarget User user);
}
