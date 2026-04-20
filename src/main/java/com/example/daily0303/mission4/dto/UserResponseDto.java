package com.example.daily0303.mission4.dto;

import com.example.daily0303.mission4.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String username;
    private final String email;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
