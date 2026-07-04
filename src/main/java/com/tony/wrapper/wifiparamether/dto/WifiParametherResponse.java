package com.tony.wrapper.wifiparamether.dto;

public class WifiParametherResponse {
    private String cpeId;
    private String wifiBand;
    private String ssid;
    private String encryptionType;
    private String password;

    public WifiParametherResponse() {
    }

    public WifiParametherResponse(String cpeId, String wifiBand, String ssid, String encryptionType, String password) {
        this.cpeId = cpeId;
        this.wifiBand = wifiBand;
        this.ssid = ssid;
        this.encryptionType = encryptionType;
        this.password = password;
    }

    public String getCpeId() {
        return cpeId;
    }

    public void setCpeId(String cpeId) {
        this.cpeId = cpeId;
    }

    public String getWifiBand() {
        return wifiBand;
    }

    public void setWifiBand(String wifiBand) {
        this.wifiBand = wifiBand;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
