package com.decipherx.fingerprint.idp.DTOs;

public class QRCodeRequestDTO {

    private String username;

    private String clientId;

    public QRCodeRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
