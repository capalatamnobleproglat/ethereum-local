package com.att.ethereumlocal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class EthereumService {

    @Value("${ethereum.node-url}")
    private String nodeUrl;

    private Web3j web3j;

    @PostConstruct
    public void init() {
        this.web3j = Web3j.build(new HttpService(nodeUrl));
    }

    public String getLastBlock() throws Exception {
        EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
        return "Último bloque: " + ethBlock.getBlock().getNumber();
    }

    public List<String> getAccounts() throws Exception {
        return web3j.ethAccounts().send().getAccounts();
    }

    public String sendTransaction() throws IOException {
        List<String> accounts = web3j.ethAccounts().send().getAccounts();

        if (accounts.isEmpty()) {
            return "No hay cuentas disponibles en el nodo Ethereum.";
        }

        String myAccount = accounts.get(0);

        String toAddress;
        if (accounts.size() > 1) {
            toAddress = accounts.get(1);
        } else {
            toAddress = accounts.get(0);
        }

        BigInteger value = BigInteger.valueOf(1000);
        BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L);
        BigInteger gasLimit = BigInteger.valueOf(21_000);

        Transaction transaction = Transaction.createEtherTransaction(
                myAccount,
                null,
                gasPrice,
                gasLimit,
                toAddress,
                value
        );

        EthSendTransaction response = web3j.ethSendTransaction(transaction).send();

        if (response.getError() != null) {
            return "Error al enviar la transacción: " + response.getError().getMessage();
        } else {
            return "Transacción enviada con éxito. Hash de la transacción: " + response.getTransactionHash();
        }
    }

    public String getBalanceWei(String address) throws Exception {
        EthGetBalance balanceWei = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        BigInteger balance = balanceWei.getBalance();
        return "Balance: " + balance + " wei";
    }

    public String getBalanceEthereum(String address) throws IOException {
        BigInteger balanceWei = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        BigDecimal balanceEther = Convert.fromWei(new BigDecimal(balanceWei), Convert.Unit.ETHER);
        return "Balance: " + balanceEther + " ether";
    }

    public String getTransactionDetails(String txHash) throws Exception {
        EthTransaction ethTransaction = web3j.ethGetTransactionByHash(txHash).send();
        Optional<org.web3j.protocol.core.methods.response.Transaction> transactionOptional = ethTransaction.getTransaction();
        if (transactionOptional.isPresent()) {
            org.web3j.protocol.core.methods.response.Transaction transaction = transactionOptional.get();
            return "Hash: " + transaction.getHash() + "\n" +
                    "From: " + transaction.getFrom() + "\n" +
                    "To: " + transaction.getTo() + "\n" +
                    "Value: " + transaction.getValue() + "\n" +
                    "Gas: " + transaction.getGas() + "\n" +
                    "Gas Price: " + transaction.getGasPrice() + "\n" +
                    "Nonce: " + transaction.getNonce() + "\n" +
                    "Input: " + transaction.getInput() + "\n" +
                    "Block Hash: " + transaction.getBlockHash() + "\n" +
                    "Transaction Index: " + transaction.getTransactionIndex();
        } else {
            throw new Exception("Transaction not found");
        }
    }
}
