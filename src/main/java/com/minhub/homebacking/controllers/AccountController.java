package com.minhub.homebacking.controllers;

import com.minhub.homebacking.dtos.AccountDTO;
import com.minhub.homebacking.models.Account;
import com.minhub.homebacking.models.Client;
import com.minhub.homebacking.repositories.AccountRepository;
import com.minhub.homebacking.repositories.ClientRepository;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")

public class AccountController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value = "/accounts",method = RequestMethod.GET)
    public List<AccountDTO> getClients() {
        return this.accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    @RequestMapping(value = "/clients/current/accounts",method = RequestMethod.GET)
    public List<AccountDTO> getAccount(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return this.accountRepository.findAccountByClient(client).stream().map(AccountDTO::new).collect(toList());
    }

    /* @RequestMapping("/accounts/{id}")
     public AccountDTO getClient(@PathVariable Long id){
         return this.accountRepository.findById(id).map(AccountDTO::new).orElse(null);

     }*/
    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id, Authentication authentication){

        Client client = this.clientRepository.findByEmail(authentication.getName());
        Set<Account> cuentas = client.getAccounts();

        Iterator iter = cuentas.iterator();
        while (iter.hasNext()){
            Account cuenta = (Account) iter.next();
            if (cuenta.getId() == id){
                return this.accountRepository.findById(id).map(AccountDTO::new).orElse(null);
            }
        }
        return null;
    }

   @RequestMapping ("/clients/current/accounts")
    public ResponseEntity<Object> getCrearAccount(Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());

        Random random = new Random();
       int value = random.nextInt(999999) ;
        if(client.getAccounts().size()<=3){
            String numero="VIN-"+value;
            Account account = new Account(numero,LocalDateTime.now(),0,client);
            accountRepository.save(account);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
       return null;
   }

    /*public int getRandomNumber() {
        int min = 1;
        int max = 9;

        Random random = new Random();

        int value = random.nextInt(max + min) + min;
        return value;
    }*/

}
