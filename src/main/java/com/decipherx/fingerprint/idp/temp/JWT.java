package com.decipherx.fingerprint.idp.temp;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Date;
import javax.crypto.*;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jwt.*;

//https://gist.github.com/destan/b708d11bd4f403506d6d5bb5fe6a82c5
//https://connect2id.com/products/nimbus-jose-jwt/examples/jwt-with-rsa-signature

class JWT{
    // RSA signatures require a public and private RSA key pair, the public key
    // must be made known to the JWS recipient in order to verify the signatures

    public static void main(String[] args) throws JOSEException, NoSuchAlgorithmException, ParseException, InvalidKeySpecException {

        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        KeyPair kp = keyGenerator.genKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)kp.getPrivate();

        String publicKeyContent = String.valueOf(Base64.encode(publicKey.getEncoded()));
        String privateKeyContent = String.valueOf(Base64.encode(privateKey.getEncoded()));


        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

        System.out.println("Public key : " + publicKeyContent );

        System.out.println("Private key : " + privateKeyContent);



        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        System.out.println(privKey);
        System.out.println(pubKey);


        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(privKey);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("alice")
                .issuer("https://fidp.citrix.com")
                .audience("sanket")
                .subject("clientapp.com")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.RS256),
                claimsSet);

        // Compute the RSA signature
        signedJWT.sign(signer);

        // To serialize to compact form, produces something like
        // eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
        // mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
        // maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
        // -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
        String s = signedJWT.serialize();
        System.out.println("JWT token : " + s);


        // On the consumer side, parse the JWS and verify its RSA signature
        signedJWT = SignedJWT.parse(s);

        JWSVerifier verifier = new RSASSAVerifier(pubKey);
        System.out.println("Verified : " + signedJWT.verify(verifier));

        // Retrieve / verify the JWT claims according to the app requirements
//        assertEquals("alice", signedJWT.getJWTClaimsSet().getSubject());
//        assertEquals("https://c2id.com", signedJWT.getJWTClaimsSet().getIssuer());
//        assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());

    }


}
