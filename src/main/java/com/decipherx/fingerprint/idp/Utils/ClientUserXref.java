package com.decipherx.fingerprint.idp.Utils;

import com.decipherx.fingerprint.idp.entities.Client;
import com.decipherx.fingerprint.idp.entities.UserDevice;
import org.springframework.beans.factory.annotation.Value;

public class ClientUserXref {

    @Value("${session.valid.seconds}")
    private Integer sessionValidityInSeconds;

    //private String id;

    private Client client;

    private UserDevice userDevice;

    private Boolean accessGranted = false;

    private Long validUntil;

    private Boolean notificationSent;

    public ClientUserXref() {
    }

    public ClientUserXref(Client client, UserDevice userDevice, Boolean accessGranted, Boolean notificationSent) {
        this.client = client;
        this.userDevice = userDevice;
        this.accessGranted = accessGranted;
        this.validUntil = (System.currentTimeMillis() / 1000 ) + 30;
        this.notificationSent = notificationSent;
        //this.id = client.getClientId() + ":" + userDevice.getUsername();
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public UserDevice getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(UserDevice userDevice) {
        this.userDevice = userDevice;
    }

    public Boolean getAccessGranted() {
        return accessGranted;
    }

    public void setAccessGranted(Boolean accessGranted) {
        this.accessGranted = accessGranted;
    }

    public Long getValidUntil() {
        return validUntil;
    }

    public Boolean getNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(Boolean notificationSent) {
        this.notificationSent = notificationSent;
    }
}
