package com.att.ethereumlocal.utils;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class EthWalletUtils {

    public static ECKeyPair createKeys() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        return Keys.createEcKeyPair();
    }

    public static Credentials getCredentialsFromECKeyPair(ECKeyPair ecKeyPair) {
        return Credentials.create(ecKeyPair);
    }

    public static String getAddressFromECKeyPair(ECKeyPair ecKeyPair) {
        return "0x" + Keys.getAddress(ecKeyPair);
    }
}
