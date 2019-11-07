package com.j25.pollsterservice.mapper;

import com.j25.pollsterservice.model.Account;
import com.j25.pollsterservice.model.dto.CreateNewAccountRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account createNewAccountFromDto(CreateNewAccountRequest request);

}
