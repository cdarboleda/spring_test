package com.security.service.dto;

import java.util.List;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class UserDTO {
    
    private String username;
    private String email;
    private List<String> roles;

}
