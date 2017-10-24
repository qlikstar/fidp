package com.decipherx.fingerprint.idp.services;

import com.decipherx.fingerprint.idp.entities.Client;
import com.decipherx.fingerprint.idp.repositories.ClientRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class JWTTokenService {

    //@Autowired
    //private KeyGeneratorService keyGeneratorService;

    @Resource
    private ClientService clientService;

    private String JWT_TOKEN;

    private String username;

    private String clientId;

    private final String ISSUER = "https://fidp.citrix.com";

    private KeyFactory kf = KeyFactory.getInstance("RSA");

    public JWTTokenService() throws NoSuchAlgorithmException {
    }

//    public void setClientDetails (String username, String clientId) throws JOSEException, InvalidKeySpecException {
//        this.username = username;
//        this.clientId = clientId;
//        createJWTToken();
//    }

    public String getJwtToken() {
        return JWT_TOKEN;
    }

    public String createJWTToken(String username, String clientId) throws JOSEException, InvalidKeySpecException {

        Client clientEntity = clientService.getClientByClientId(clientId);
        if (null != clientEntity) {

            // Create RSA-signer with the private keyxxxxxxxxxx
            //KeyGeneratorService keyGeneratorService = new KeyGeneratorService();

            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(clientEntity.getPrivateKey()));
            PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);
            JWSSigner signer = new RSASSASigner(privKey);

            // Prepare JWT with claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issuer(ISSUER)
                    .audience(username)
                    .subject("QR Code JWT Token")
                    .claim("clientId", clientId)
                    .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.RS256),
                    claimsSet);

            signedJWT.sign(signer);
            JWT_TOKEN = signedJWT.serialize();

        }else{
            //TODO: Throw exception
        }
        return JWT_TOKEN;
    }

    public Boolean checkIfTokenIsValid(String jwtToken, String publicKeyContent) throws InvalidKeySpecException, ParseException, JOSEException {

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
        SignedJWT signedJWT = SignedJWT.parse(jwtToken);

        JWSVerifier verifier = new RSASSAVerifier(pubKey);
        return  signedJWT.verify(verifier);

    }

    public static void main1(String[] args) throws JOSEException, NoSuchAlgorithmException {
        JWTTokenService jwt = new JWTTokenService();
        //System.out.println(jwt.getJwtToken());
    }

}
