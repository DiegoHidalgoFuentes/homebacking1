package com.minhub.homebacking.controllers;

import com.minhub.homebacking.dtos.CardDTO;
import com.minhub.homebacking.models.*;
import com.minhub.homebacking.repositories.CardRepository;
import com.minhub.homebacking.repositories.ClientRepository;
import org.aspectj.weaver.bcel.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients/currents/cards")
    public List<CardDTO> getCards (Authentication authentication){
        Client client=this.clientRepository.findByEmail(authentication.getName());
        return client.getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }
    @RequestMapping (value = "/clients/current/cards",method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType cardType,@RequestParam CardColor cardColor){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        Random ramdom= new Random();
        int num1 = ramdom.nextInt(9999-1000)+10;
        int num2 = ramdom.nextInt(9999-1000)+10;
        int num3 = ramdom.nextInt(9999-1000)+10;
        int num4 = ramdom.nextInt(9999-1000)+10;
        int num5 = ramdom.nextInt(999-100)+10;
        String numero=num1+" "+num2+" "+num3+" "+num4;
         if(client.getCards().stream().filter(card -> card.getType().toString().equals(cardType.toString())).count()>=3){

            return new ResponseEntity<>("ya tiene 3 tarjetas en pocesion ",HttpStatus.FORBIDDEN);
        }
         cardRepository.save(new Card(client.getFirstName()+" "+client.getLastName(),cardType,cardColor,numero,num5,LocalDateTime.now(),LocalDateTime.now().plusYears(5),client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
