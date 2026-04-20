package com.example.daily0303.mission5.dto;

import com.example.daily0303.mission5.domain.Account;
import lombok.Getter;

@Getter
public class AccountResponseDto {

    private final Long id;
    private final String accountNumber;
    private final String owner;
    private final long balance;

    public AccountResponseDto(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.owner = account.getOwner();
        this.balance = account.getBalance();
    }
}
