package com.example.daily0303.mission5.controller;

import com.example.daily0303.mission5.dto.AccountRequestDto;
import com.example.daily0303.mission5.dto.AccountResponseDto;
import com.example.daily0303.mission5.dto.TransferRequestDto;
import com.example.daily0303.mission5.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mission5/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDto> create(@RequestBody AccountRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> findByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.findByAccountNumber(accountNumber));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transfer(@RequestBody TransferRequestDto dto) {
        accountService.transfer(dto);
        return ResponseEntity.ok(Map.of("result", "송금 성공"));
    }

    @PostMapping("/transfer/rollback-test")
    public ResponseEntity<Map<String, String>> transferWithRollback(@RequestBody TransferRequestDto dto) {
        try {
            accountService.transferWithForcedRollback(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("result", "롤백 발생", "message", e.getMessage()));
        }
        return ResponseEntity.ok(Map.of("result", "완료"));
    }
}
