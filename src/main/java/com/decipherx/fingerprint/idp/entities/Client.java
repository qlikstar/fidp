package com.decipherx.fingerprint.idp.entities;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String clientId;

    @Column (nullable = false, unique = true)
    private String appName;

    @Column (nullable = false)
    private String fqdn;

    @Column (nullable = false, unique = true, columnDefinition = "TEXT")
    private String publicKey;

    @Column (nullable = false, unique = true, columnDefinition = "TEXT")
    private String privateKey;

//    @ManyToOne
//    private ClientUserXref clientUserXref;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

//    public ClientUserXref getClientUserXref() {
//        return clientUserXref;
//    }
//
//    public void setClientUserXref(ClientUserXref clientUserXref) {
//        this.clientUserXref = clientUserXref;
//    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", appName='" + appName + '\'' +
                ", fqdn='" + fqdn + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
