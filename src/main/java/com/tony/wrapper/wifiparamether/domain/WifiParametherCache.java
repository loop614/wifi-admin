package com.tony.wrapper.wifiparamether.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.tony.wrapper.wifiparamether.dto.WifiParametherResponse;
import com.tony.wrapper.wifiparamether.entity.WifiConfiguration;
import com.tony.wrapper.wifiparamether.repository.WifiConfigurationRepository;

import jakarta.persistence.EntityManager;
import local.wifi_admin.platform.v1.EncryptionType;
import local.wifi_admin.platform.v1.WifiBandType;

@Service
public class WifiParametherCache {

    private final EntityManager entityManager;
    private final WifiConfigurationRepository wifiConfigurationRepository;

    public WifiParametherCache(EntityManager entityManager, WifiConfigurationRepository wifiConfigurationRepository) {
        this.entityManager = entityManager;
        this.wifiConfigurationRepository = wifiConfigurationRepository;
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

    void deleteByCpeId(String cpeId) {
        this.wifiConfigurationRepository.deleteByCpeId(cpeId);
    }

    void deleteByCpeIdInOrDateUpdatedBefore(ArrayList<String> arrayList, LocalDateTime minusDays) {
        this.wifiConfigurationRepository.deleteByCpeIdInOrDateUpdatedBefore(arrayList, minusDays);
    }
}
