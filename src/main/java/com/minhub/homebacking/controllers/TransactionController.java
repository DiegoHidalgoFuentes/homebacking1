package com.minhub.homebacking.controllers;

import com.minhub.homebacking.dtos.TransactionDTO;
import com.minhub.homebacking.models.Account;
import com.minhub.homebacking.models.Client;
import com.minhub.homebacking.models.Transaction;
import com.minhub.homebacking.models.TransactionType;
import com.minhub.homebacking.repositories.AccountRepository;
import com.minhub.homebacking.repositories.ClientRepository;
import com.minhub.homebacking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;



@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping("/transaction")
    public List<TransactionDTO> getTransaction() {
        return this.transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());

    }

    @RequestMapping("/transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return this.transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }

    @Transactional
    @RequestMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
                                                  @RequestParam double amount,@RequestParam String description) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        List<Account> accountList=accountRepository.findAccountByClient(client);
        if(accountRepository.findAccountByNumber(fromAccountNumber)==null){
            return new ResponseEntity<>("La cuenta origen no puede ser nula",HttpStatus.FORBIDDEN);
        }
        if(accountRepository.findAccountByNumber(toAccountNumber)==null){
            return new ResponseEntity<>("La cuenta destino no puede ser nula",HttpStatus.FORBIDDEN);
        }
        if(amount<=0 || description==null || toAccountNumber==null|| fromAccountNumber==null){
            return new ResponseEntity<>("Los datos no pueden ser nulos",HttpStatus.FORBIDDEN);
        }
        if(fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Las cuentas no deben ser iguales",HttpStatus.FORBIDDEN);
        }
        boolean find=false;
        Account fronaccount=null;
        for (Account account1: accountList){
            if (account1.getNumber().equals(fromAccountNumber)){
                find=true;
                fronaccount=account1;
            }
        }
        if(find==false){
            return new ResponseEntity<>("La cuenta no pertenece al usuario ",HttpStatus.FORBIDDEN);
        }
        if(amount>=fronaccount.getBalance()){
            return new ResponseEntity<>("No tiene fondos suficientes",HttpStatus.FORBIDDEN);
        }
        transactionRepository.save(new Transaction(TransactionType.DEBIT, -amount, "Transferencia a :" + toAccountNumber, LocalDateTime.now(), accountRepository.findAccountByNumber(fromAccountNumber)));
        transactionRepository.save(new Transaction(TransactionType.CREDIT, amount, "Transferencia de :" + fromAccountNumber, LocalDateTime.now(), accountRepository.findAccountByNumber(toAccountNumber)));
        Account toAccount= accountRepository.findAccountByNumber(toAccountNumber);
        fronaccount.setBalance(fronaccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
