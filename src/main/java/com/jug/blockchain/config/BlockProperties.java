package com.jug.blockchain.config;

import java.math.BigInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.web3j.tx.gas.StaticGasProvider;

import lombok.Getter;
import lombok.Setter;


@Configuration
@ConfigurationProperties(prefix = "block.contract")
@Getter
@Setter
public class BlockProperties {

    private String ownerAddress;

    private BigInteger gasPrice;
    private BigInteger gasLimit;
    
    private String blockNumber;
    private BigInteger acumulado;
 
    
    public StaticGasProvider gas() {
        return new StaticGasProvider(gasPrice, gasLimit);
    }
}