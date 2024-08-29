package com.ylab.mapper;

import com.ylab.entity.User;
import com.ylab.entity.dto.user.UserForRegisterDto;
import com.ylab.entity.dto.user.UserForShowAndUpdateRoleDto;
import com.ylab.entity.dto.user.UserUpdateDto;
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

    User toUser(UserForRegisterDto userDto);

    void updateOwnProfile(UserUpdateDto userDto, @MappingTarget User user);

    void updateRole(UserForShowAndUpdateRoleDto userForShowDto, @MappingTarget User user);

    UserForShowAndUpdateRoleDto toUserForShowDto(User user);
}
