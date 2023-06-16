package com.srdevpereira.controller;

import com.srdevpereira.entities.Coin;
import com.srdevpereira.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@RestController
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    private CoinRepository coinRepository;

    @Bean
    public Coin init(){
        Coin c1 = new Coin();
        c1.setName("BITCOIN");
        c1.setPrice(new BigDecimal(100));
        c1.setQuantity(new BigDecimal(0.005));
        c1.setDateTime(new Timestamp(System.currentTimeMillis()));

        Coin c2 = new Coin();
        c2.setName("BITCOIN");
        c2.setPrice(new BigDecimal(200));
        c2.setQuantity(new BigDecimal(0.01));
        c2.setDateTime(new Timestamp(System.currentTimeMillis()));

        Coin c3 = new Coin();
        c3.setName("ETHEREUM");
        c3.setPrice(new BigDecimal(500));
        c3.setQuantity(new BigDecimal(0.100));
        c3.setDateTime(new Timestamp(System.currentTimeMillis()));

        coinRepository.insert(c1);
        coinRepository.insert(c2);
        coinRepository.insert(c3);

        return c1;
    }

    @GetMapping()
    public ResponseEntity get(){
        return new ResponseEntity<>(coinRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity get(@PathVariable String name){
        try{
            return new ResponseEntity<>(coinRepository.getByName(name), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping() //como só teremos 1 rota de post não se faz necessario determinar um novo end-point para ele
    public ResponseEntity post(@RequestBody Coin coin){ //esta anotação define que os detalhes da requisição estarão no corpo do arquivo Json
        try{
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));//define a data sistemica do momento da requisição
            return new ResponseEntity<>(coinRepository.insert(coin), HttpStatus.CREATED);
        }
        catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@RequestBody Coin coin){
        try{
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(coinRepository.update(coin), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id){
        try{
            return new ResponseEntity<>(coinRepository.remove(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

}
