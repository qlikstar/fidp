package com.decipherx.fingerprint.idp.DTOs;

public class ClientRequestDTO {

    private String appname;

    private String fqdn;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public ClientRequestDTO() {
    }

    public ClientRequestDTO(String appname, String fqdn) {
        this.appname = appname;
        this.fqdn = fqdn;
    }
}
