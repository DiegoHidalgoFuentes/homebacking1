package com.minhub.homebacking.controllers;

import com.minhub.homebacking.dtos.ClientDTO;
import com.minhub.homebacking.models.Account;
import com.minhub.homebacking.models.Client;
import com.minhub.homebacking.repositories.AccountRepository;
import com.minhub.homebacking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")

public class ClientController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;



    /*@RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<Client> getClient(){
        return this.clientRepository.findAll();
    }*/

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return this.clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }


    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return this.clientRepository.findById(id).map(ClientDTO::new).orElse(null);

    }
    //mostrar clientes actuales en session
    @RequestMapping("/clients/current")
    public ClientDTO getClients(Authentication authentication){
    Client client = this.clientRepository.findByEmail(authentication.getName());
    return new ClientDTO(client);
    }

   /* @RequestMapping("/current")
    public List<ClientDTO> getAll(Authentication authentication){
       Client client=this.clientRepository.findByEmail(authentication.getName());
        return new List<ClientDTO>(client);
    }*/

    @RequestMapping(path="/clients",method = RequestMethod.POST)
    public ResponseEntity<Object> createClient(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {



        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>( "Datos faltantes" , HttpStatus.FORBIDDEN);

        }



        if (clientRepository.findByEmail(email) !=  null ) {

            return new ResponseEntity<>( "Nombre ya en uso" , HttpStatus.FORBIDDEN);

        }



        Client client=clientRepository.save( new Client(firstName, lastName, email , passwordEncoder.encode(password)));

        Random random = new Random();
        int value = random.nextInt(999999) ;

            String numero="VIN-"+value;
            accountRepository.save(new Account(numero, LocalDateTime.now(),0,client));



        return new ResponseEntity<>(HttpStatus.CREATED);

        }



    /*@RequestMapping("/clients/find/{mail}")
    public ClientDTO getClientByMail(@PathVariable String mail){
        return new ClientDTO(clientRepository.findByEmail(mail));
    }

    @RequestMapping("/clients/find/name/{name}")
    public List<ClientDTO> getClientByFirstName(@PathVariable String name){
        Sort sort =Sort.by("firstName").descending();
        return  this.clientRepository.findByFirstname(name, sort).stream().map(ClientDTO::new).collect(toList());
    }*/
   /* @RequestMapping("/clients")
    public List<ClientDTO> finAll(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }
    @RequestMapping("/clients/{id}")
    public ClientDTO getClients (@PathVariable Long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }*/

}
