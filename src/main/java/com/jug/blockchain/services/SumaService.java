package com.jug.blockchain.services;

import com.jug.blockchain.config.BlockProperties;
import com.jug.blockchain.contracts.SimpleSuma;
import java.math.BigInteger;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;

import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;


import lombok.AllArgsConstructor;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;

/**
 * ElectionsService
 */
@AllArgsConstructor
public class SumaService {

    private static final Logger LOG = LoggerFactory.getLogger(SumaService.class);

    private final String contractAddress;
    private final Web3j web3j;
    private final BlockProperties config;

    @SuppressWarnings("unchecked")
    public BigInteger getData() throws Exception {
        SimpleSuma contrato = loadContract(config.getOwnerAddress());
        
        RemoteCall<BigInteger> remoteCall = contrato.getSuma();
        
        BigInteger suma = remoteCall.send();
        
        
       return suma;
           
        
    }

    public BigInteger setData(BigInteger data) throws Exception {
        

        SimpleSuma contrato = loadContract(config.getOwnerAddress());

        contrato.setSuma(data).send();

        return data;
    }

    private SimpleSuma loadContract(String accountAddress) {
        return SimpleSuma.load(contractAddress, web3j, transactionManager(accountAddress), config.gas());
    }

    private TransactionManager transactionManager(String accountAddress) {
        return new ClientTransactionManager(web3j, accountAddress);
    }

   
    
}