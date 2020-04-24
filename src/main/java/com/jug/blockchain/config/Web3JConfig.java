package com.jug.blockchain.config;



import com.jug.blockchain.contracts.SimpleSuma;
import com.jug.blockchain.services.SumaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import okhttp3.OkHttpClient;

/**
 * Web3JConfig
 */
@Configuration
public class Web3JConfig {

    private static final Logger LOG = LoggerFactory.getLogger(Web3JConfig.class);

    @Value("${web3j.client-address}")
    private String clientAddress;

    @Autowired
    private BlockProperties config;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(clientAddress, new OkHttpClient.Builder().build()));
    }

    @Bean
    public SumaService contract(Web3j web3j, @Value("${block.contract.address}") String contractAddress)
            throws Exception {
        if (StringUtils.isEmpty(contractAddress)) {
            SimpleSuma contrato = deployContract(web3j);
            contractAddress = contrato.getContractAddress();
            System.setProperty("block.contract.address", contractAddress);
        }

        return initElectionsService(contractAddress, web3j);
    }

    private SumaService initElectionsService(String contractAddress, Web3j web3j) {
        return new SumaService(contractAddress, web3j, config);
    }

    private SimpleSuma deployContract(Web3j web3j) throws Exception {
        LOG.info("Deploying new contract in Ethereum.");
        SimpleSuma contract = SimpleSuma.deploy(web3j, transactionManager(web3j), config.gas()).send();
        LOG.info("Deployed new contract with address '{}'", contract.getContractAddress());
        return contract;
    }

    private TransactionManager transactionManager(Web3j web3j) {
        return new ClientTransactionManager(web3j, config.getOwnerAddress());
    }
}