package com.tony.wrapper.wifiparamether.domain;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tony.wrapper.soapClient.SoapClientService;
import com.tony.wrapper.wifiparamether.dto.WifiParametherResponse;
import com.tony.wrapper.wifiparamether.entity.WifiConfiguration;
import com.tony.wrapper.wifiparamether.repository.WifiConfigurationRepository;

import local.wifi_admin.platform.v1.EncryptionType;
import local.wifi_admin.platform.v1.WifiBandType;

@ExtendWith(MockitoExtension.class)
class WifiParametherDispatcherTest {

    @Mock
    private SoapClientService soapClient;

    @Mock
    private WifiConfigurationRepository wifiConfigurationRepository;

    @Mock
    private WifiParametherCache wifiParametherCache;

    @Test
    void getCpeIdShouldUseCachedConfigurationWhenAvailable() {
        WifiConfiguration cachedConfiguration = new WifiConfiguration();
        cachedConfiguration.setCpeId("cpe-123");
        cachedConfiguration.setWifiBand(WifiBandType.BAND_2_4_GHZ);
        cachedConfiguration.setSsid("CachedWiFi");
        cachedConfiguration.setEncryptionType(EncryptionType.OPEN);
        cachedConfiguration.setPassword("secret");

        when(wifiConfigurationRepository.getByCpeId("cpe-123")).thenReturn(Optional.of(cachedConfiguration));

        WifiParametherDispatcher dispatcher = new WifiParametherDispatcher(soapClient, wifiConfigurationRepository, wifiParametherCache);

        WifiParametherResponse response = dispatcher.getCpeId("cpe-123");

        assertEquals("cpe-123", response.getCpeId());
        assertEquals("BAND_2_4_GHZ", response.getWifiBand());
        assertEquals("CachedWiFi", response.getSsid());
        assertEquals("OPEN", response.getEncryptionType());
        assertEquals("secret", response.getPassword());
        verify(wifiConfigurationRepository).getByCpeId("cpe-123");
        verifyNoInteractions(soapClient);
    }
}
