package com.j25.pollsterservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAccountRequest {


    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String userEmail;
    @NotNull
    private String phone;

}
