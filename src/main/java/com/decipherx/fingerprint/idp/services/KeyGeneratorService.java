package com.decipherx.fingerprint.idp.services;

import com.nimbusds.jose.util.Base64;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Service
public class KeyGeneratorService {

    private String ALGORITHM = "RSA";
    private Integer KEYSIZE = 2048;

    private String PUBLIC_KEY_PEM;
    private String PRIVATE_KEY_PEM;

    private RSAPublicKey PUBLIC_KEY_RSA;
    private RSAPrivateKey PRIVATE_KEY_RSA;

    public String getPublicKeyPem() {
        return PUBLIC_KEY_PEM;
    }

    public String getPrivateKeyPem() {
        return PRIVATE_KEY_PEM;
    }

    public RSAPublicKey getPublicKeyRsa() {
        return PUBLIC_KEY_RSA;
    }

    public RSAPrivateKey getPrivateKeyRsa() {
        return PRIVATE_KEY_RSA;
    }

    public KeyGeneratorService() {
        try {
            generateKeys();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void generateKeys() throws NoSuchAlgorithmException {

        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyGenerator.initialize(KEYSIZE);

        KeyPair kp = keyGenerator.genKeyPair();
        PUBLIC_KEY_RSA = (RSAPublicKey) kp.getPublic();
        PRIVATE_KEY_RSA = (RSAPrivateKey) kp.getPrivate();

        PUBLIC_KEY_PEM = String.valueOf(Base64.encode(PUBLIC_KEY_RSA.getEncoded()));
        PRIVATE_KEY_PEM = String.valueOf(Base64.encode(PRIVATE_KEY_RSA.getEncoded()));
    }

    public static void main1(String[] args) {
        KeyGeneratorService keyGeneratorService = new KeyGeneratorService();
        //System.out.println(keyGeneratorService.PRIVATE_KEY_PEM);
    }


}
