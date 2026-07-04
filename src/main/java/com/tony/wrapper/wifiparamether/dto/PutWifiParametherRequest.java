package com.tony.wrapper.wifiparamether.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PutWifiParametherRequest {
    @NotBlank(message = "cpeId is required")
    private String cpeId;

    @NotBlank(message = "wifiBand is required")
    @Pattern(regexp = "BAND_2_4_GHZ|BAND_5_GHZ", message = "wifiBand must be BAND_2_4_GHZ or BAND_5_GHZ")
    private String wifiBand;

    @NotBlank(message = "ssid is required")
    private String ssid;

    @Pattern(regexp = "OPEN|WEP|WPA_PSK|WPA2_PSK|WPA3_SAE|WPA2_ENTERPRISE", message = "encryptionType must be one of the supported values")
    private String encryptionType;
    private String password;

    public PutWifiParametherRequest() {
    }

    public PutWifiParametherRequest(String cpeId, String wifiBand, String ssid, String encryptionType, String password) {
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
