/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jug.blockchain.rest;

import com.jug.blockchain.services.SumaService;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.RemoteCall;

/**
 *
 * @author RICARDO
 */
@RestController
@CrossOrigin(origins="*")
public class SumaController {
    
    @Autowired
    private SumaService sumaService;
    
    
    @GetMapping("/get-data")
    public ResponseEntity<BigInteger> getData() throws Exception {
        return ResponseEntity.ok().body(sumaService.getData());
    }
    
    @PostMapping("/set-data")
    public ResponseEntity<BigInteger> setData(@RequestBody(required = true) BigInteger data) throws Exception {
        BigInteger response = null;
        try {
            response = sumaService.setData(data);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    
    
}
