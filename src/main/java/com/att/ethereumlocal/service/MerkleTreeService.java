package com.att.ethereumlocal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerkleTreeService {

    @Autowired
    private HashingService hashingService;

    public String generateMerkleRoot(List<String> transactions) throws NoSuchAlgorithmException {
        List<String> tempTxList = transactions;
        List<String> newTxList = getNewHashList(tempTxList);
        while (newTxList.size() != 1) {
            newTxList = getNewHashList(newTxList);
        }
        return newTxList.get(0);
    }

    private List<String> getNewHashList(List<String> tempTxList) throws NoSuchAlgorithmException {
        List<String> newTxList = new ArrayList<>();
        int index = 0;
        while (index < tempTxList.size()) {
            // Left
            String left = tempTxList.get(index);
            index++;

            // Right
            String right = "";
            if (index != tempTxList.size()) {
                right = tempTxList.get(index);
            }

            // SHA2 hex value
            String hashValue = hashingService.hashData(left + right);
            newTxList.add(hashValue);
            index++;
        }
        return newTxList;
    }
}
