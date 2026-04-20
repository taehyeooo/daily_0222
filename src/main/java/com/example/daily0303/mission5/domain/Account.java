package com.example.daily0303.mission5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mission5_accounts")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private long balance;

    public Account(String accountNumber, String owner, long balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }

    public void withdraw(long amount) {
        if (this.balance < amount) {
            throw new IllegalStateException("잔액이 부족합니다. 현재 잔액: " + this.balance);
        }
        this.balance -= amount;
    }

    public void deposit(long amount) {
        if (amount <= 0) {
            throw new IllegalStateException("입금액은 0보다 커야 합니다.");
        }
        this.balance += amount;
    }
}
