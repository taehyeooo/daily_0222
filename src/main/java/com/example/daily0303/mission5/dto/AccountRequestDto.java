package com.example.daily0303.mission5.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequestDto {
    private String accountNumber;
    private String owner;
    private long balance;
}
