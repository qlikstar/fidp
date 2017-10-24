package com.decipherx.fingerprint.idp.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QrResponseDTO {

    private String qrCode;

    @Value("${downloadlink.android}")
    private String androidAppDownloadLink;

    @Value("${downloadlink.ios}")
    private String iosAppDownloadLink;

    @Value("${downloadlink.android}")
    private String googlePlayDownloadButtonImg;

    @Value("${downloadlink.ios}")
    private String appstoreDownloadButtonImg;

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getAndroidAppDownloadLink() {
        return androidAppDownloadLink;
    }

    public String getIosAppDownloadLink() {
        return iosAppDownloadLink;
    }

    public String getGooglePlayDownloadButtonImg() {
        return googlePlayDownloadButtonImg;
    }

    public String getAppstoreDownloadButtonImg() {
        return appstoreDownloadButtonImg;
    }

    @Override
    public String toString() {
        return "QrResponseDTO{" +
                "qrCode='" + qrCode + '\'' +
                ", androidAppDownloadLink='" + androidAppDownloadLink + '\'' +
                ", iosAppDownloadLink='" + iosAppDownloadLink + '\'' +
                ", googlePlayDownloadButtonImg='" + googlePlayDownloadButtonImg + '\'' +
                ", appstoreDownloadButtonImg='" + appstoreDownloadButtonImg + '\'' +
                '}';
    }
}
