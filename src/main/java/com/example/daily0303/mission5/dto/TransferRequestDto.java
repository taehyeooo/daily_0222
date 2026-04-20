package com.example.daily0303.mission5.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDto {
    private String fromAccountNumber;
    private String toAccountNumber;
    private long amount;
}
