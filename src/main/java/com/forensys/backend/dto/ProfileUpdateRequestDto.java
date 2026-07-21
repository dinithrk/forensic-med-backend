package com.forensys.backend.dto;

import lombok.Data;

@Data
public class ProfileUpdateRequestDto {
    private String username;
    private String password;
}
