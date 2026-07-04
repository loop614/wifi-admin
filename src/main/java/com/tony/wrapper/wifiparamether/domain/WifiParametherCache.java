package com.tony.wrapper.wifiparamether.domain;

import org.springframework.stereotype.Service;

import com.tony.wrapper.wifiparamether.dto.WifiParametherResponse;
import com.tony.wrapper.wifiparamether.entity.WifiConfiguration;

import jakarta.persistence.EntityManager;
import local.wifi_admin.platform.v1.EncryptionType;
import local.wifi_admin.platform.v1.UpdateCpeIdResponse;
import local.wifi_admin.platform.v1.WifiBandType;
import local.wifi_admin.platform.v1.WifiConfigurationType;

@Service
public class WifiParametherCache {

    private final EntityManager entityManager;

    public WifiParametherCache(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save (WifiParametherResponse response) {
        WifiConfiguration cachedConfiguration = new WifiConfiguration();
        cachedConfiguration.setCpeId(response.getCpeId());
        cachedConfiguration.setWifiBand(response.getWifiBand() != null ? WifiBandType.valueOf(response.getWifiBand()) : WifiBandType.BAND_2_4_GHZ);
        cachedConfiguration.setSsid(response.getSsid());
        cachedConfiguration.setEncryptionType(response.getEncryptionType() != null ? EncryptionType.valueOf(response.getEncryptionType()) : EncryptionType.OPEN);
        cachedConfiguration.setPassword(response.getPassword());
        this.entityManager.persist(cachedConfiguration);
        this.entityManager.flush();
    }

    public void saveUpdated (UpdateCpeIdResponse response) {
        WifiConfiguration cachedConfiguration = new WifiConfiguration();
        WifiConfigurationType responseConfiguration = response.getConfiguration();
        cachedConfiguration.setCpeId(responseConfiguration.getCpeId());
        cachedConfiguration.setWifiBand(responseConfiguration.getWifiBand() != null ? responseConfiguration.getWifiBand() : WifiBandType.BAND_2_4_GHZ);
        cachedConfiguration.setSsid(responseConfiguration.getSsid());
        cachedConfiguration.setEncryptionType(responseConfiguration.getEncryptionType() != null ? responseConfiguration.getEncryptionType() : EncryptionType.OPEN);
        cachedConfiguration.setPassword(responseConfiguration.getPassword());
        this.entityManager.persist(cachedConfiguration);
        this.entityManager.flush();
    }
}
