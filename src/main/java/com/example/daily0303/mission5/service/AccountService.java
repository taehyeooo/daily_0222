package com.example.daily0303.mission5.service;

import com.example.daily0303.mission5.domain.Account;
import com.example.daily0303.mission5.dto.AccountRequestDto;
import com.example.daily0303.mission5.dto.AccountResponseDto;
import com.example.daily0303.mission5.dto.TransferRequestDto;
import com.example.daily0303.mission5.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountResponseDto create(AccountRequestDto dto) {
        Account account = new Account(dto.getAccountNumber(), dto.getOwner(), dto.getBalance());
        return new AccountResponseDto(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AccountResponseDto findByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다: " + accountNumber));
        return new AccountResponseDto(account);
    }

    @Transactional
    public void transfer(TransferRequestDto dto) {
        Account from = accountRepository.findByAccountNumber(dto.getFromAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("출금 계좌를 찾을 수 없습니다: " + dto.getFromAccountNumber()));

        Account to = accountRepository.findByAccountNumber(dto.getToAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("입금 계좌를 찾을 수 없습니다: " + dto.getToAccountNumber()));

        from.withdraw(dto.getAmount());
        to.deposit(dto.getAmount());
    }

    @Transactional
    public void transferWithForcedRollback(TransferRequestDto dto) {
        Account from = accountRepository.findByAccountNumber(dto.getFromAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("출금 계좌를 찾을 수 없습니다: " + dto.getFromAccountNumber()));

        Account to = accountRepository.findByAccountNumber(dto.getToAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("입금 계좌를 찾을 수 없습니다: " + dto.getToAccountNumber()));

        from.withdraw(dto.getAmount());
        to.deposit(dto.getAmount());

        throw new RuntimeException("송금 처리 중 오류 발생 - 롤백 테스트");
    }
}
