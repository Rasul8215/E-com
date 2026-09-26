package com.e_com.project.service;

import com.e_com.project.models.User;
import com.e_com.project.validator.UserValidator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserValidator input);
}
