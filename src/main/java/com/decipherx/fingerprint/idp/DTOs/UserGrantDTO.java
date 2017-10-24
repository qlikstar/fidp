package com.decipherx.fingerprint.idp.DTOs;

import com.decipherx.fingerprint.idp.entities.UserDevice;

public class UserGrantDTO {

    private Boolean userGrant;

    private Long validUntil;

    private QrResponseDTO qrResponseDTO;

    private UserDevice userDevice;

    public UserGrantDTO() {
    }

    public Boolean getUserGrant() {
        return userGrant;
    }

    public void setUserGrant(Boolean userGrant) {
        this.userGrant = userGrant;
    }

    public Long getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Long validUntil) {
        this.validUntil = validUntil;
    }

    public QrResponseDTO getQrResponseDTO() {
        return qrResponseDTO;
    }

    public void setQrResponseDTO(QrResponseDTO qrResponseDTO) {
        this.qrResponseDTO = qrResponseDTO;
    }

    public UserDevice getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(UserDevice userDevice) {
        this.userDevice = userDevice;
    }
}
