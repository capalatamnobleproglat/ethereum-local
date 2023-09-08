package com.att.ethereumlocal.controller;

import com.att.ethereumlocal.service.EthereumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.att.ethereumlocal.service.HashingService;
import com.att.ethereumlocal.service.MerkleTreeService;
import java.util.List;

@RestController
@RequestMapping("/api/ethereum")
public class EthereumController {

    private final EthereumService ethereumService;
    private final HashingService hashingService;
    private final MerkleTreeService merkleTreeService;

    @Autowired
    public EthereumController(EthereumService ethereumService, HashingService hashingService, MerkleTreeService merkleTreeService) {
        this.ethereumService = ethereumService;
        this.hashingService = hashingService;
        this.merkleTreeService = merkleTreeService;
    }

    @GetMapping("/getLastBlock")
    public String getLastBlock() throws Exception {
        return ethereumService.getLastBlock();
    }

    @GetMapping("/getAccounts")
    public List<String> getAccounts() throws Exception {
        return ethereumService.getAccounts();
    }

    @GetMapping("/sendTransaction")
    public String sendTransaction() throws Exception {
        return ethereumService.sendTransaction();
    }

    @GetMapping("/getBalanceWei/{address}")
    public ResponseEntity<String> getBalanceWei(@PathVariable String address) {
        try {
            String balance = ethereumService.getBalanceWei(address);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving balance: " + e.getMessage());
        }
    }

    @GetMapping("/getBalanceEthereum/{address}")
    public ResponseEntity<String> getBalanceEthereum(@PathVariable String address) {
        try {
            String balance = ethereumService.getBalanceEthereum(address);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving balance: " + e.getMessage());
        }
    }

    @GetMapping("/getTransactionDetails/{txHash}")
    public ResponseEntity<String> getTransactionDetails(@PathVariable String txHash) {
        try {
            String transactionDetails = ethereumService.getTransactionDetails(txHash);
            return ResponseEntity.ok(transactionDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving transaction details: " + e.getMessage());
        }
    }

    @GetMapping("/hashData/{data}")
    public ResponseEntity<String> hashData(@PathVariable String data) {
        try {
            String hash = hashingService.hashData(data);
            return ResponseEntity.ok(hash);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error hashing data: " + e.getMessage());
        }
    }

    @PostMapping("/generateMerkleRoot")
    public ResponseEntity<String> generateMerkleRoot(@RequestBody List<String> transactions) {
        try {
            String root = merkleTreeService.generateMerkleRoot(transactions);
            return ResponseEntity.ok(root);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating Merkle Root: " + e.getMessage());
        }
    }
}
