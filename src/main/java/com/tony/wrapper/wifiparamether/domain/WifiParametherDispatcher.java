package com.tony.wrapper.wifiparamether.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tony.wrapper.soapClient.SoapClientService;
import com.tony.wrapper.wifiparamether.dto.PutWifiParametherRequest;
import com.tony.wrapper.wifiparamether.dto.PutWifiParametherResponse;
import com.tony.wrapper.wifiparamether.dto.WifiParametherResponse;
import com.tony.wrapper.wifiparamether.entity.WifiConfiguration;
import com.tony.wrapper.wifiparamether.repository.WifiConfigurationRepository;

import local.wifi_admin.platform.v1.EncryptionType;
import local.wifi_admin.platform.v1.GetCpeIdRequest;
import local.wifi_admin.platform.v1.GetCpeIdResponse;
import local.wifi_admin.platform.v1.UpdateCpeIdRequest;
import local.wifi_admin.platform.v1.UpdateCpeIdResponse;
import local.wifi_admin.platform.v1.WifiBandType;
import local.wifi_admin.platform.v1.WifiConfigurationType;

@Service
public class WifiParametherDispatcher {
    private final SoapClientService soapClient;
    private final WifiConfigurationRepository wifiConfigurationRepository;
    private final WifiParametherCache wifiParametherCache;

    public WifiParametherDispatcher(SoapClientService soapClient, WifiConfigurationRepository wifiConfigurationRepository, WifiParametherCache wifiParametherCache) {
        this.soapClient = soapClient;
        this.wifiConfigurationRepository = wifiConfigurationRepository;
        this.wifiParametherCache = wifiParametherCache;
    }

    public WifiParametherResponse getCpeId(String cpeId) {
        Optional<WifiConfiguration> cachedResponse = this.wifiConfigurationRepository.getByCpeId(cpeId);
        if (cachedResponse.isPresent()) {
            return this.toResponse(cachedResponse.get());
        }

        WifiParametherResponse soapResponse = this.fetchFromSoap(cpeId);
        this.wifiParametherCache.save(soapResponse);

        return soapResponse;
    }

    private WifiParametherResponse toResponse(WifiConfiguration configuration) {
        WifiParametherResponse result = new WifiParametherResponse();
        result.setCpeId(configuration.getCpeId());
        result.setWifiBand(configuration.getWifiBand() != null ? configuration.getWifiBand().name() : "BAND_2_4_GHZ");
        result.setSsid(configuration.getSsid());
        result.setEncryptionType(configuration.getEncryptionType() != null ? configuration.getEncryptionType().name() : "OPEN");
        result.setPassword(configuration.getPassword());

        return result;
    }

    public WifiParametherResponse fetchFromSoap(String cpeId) {
        GetCpeIdRequest request = new GetCpeIdRequest();
        request.setCpeId(cpeId);

        GetCpeIdResponse response = this.soapClient.getCpeId(request);

        WifiParametherResponse result = new WifiParametherResponse();
        result.setCpeId(cpeId);
        result.setWifiBand("BAND_2_4_GHZ");
        result.setSsid("DefaultWiFi");
        result.setEncryptionType("OPEN");

        if (response != null && response.getConfiguration() != null) {
            WifiConfigurationType configuration = response.getConfiguration();
            if (configuration.getCpeId() != null) {
                result.setCpeId(configuration.getCpeId());
            }
            if (configuration.getWifiBand() != null) {
                result.setWifiBand(configuration.getWifiBand().name());
            }
            if (configuration.getSsid() != null) {
                result.setSsid(configuration.getSsid());
            }
            if (configuration.getEncryptionType() != null) {
                result.setEncryptionType(configuration.getEncryptionType().name());
            }
            result.setPassword(configuration.getPassword());
        }

        return result;
    }

    public PutWifiParametherResponse updateCpeId(PutWifiParametherRequest request) {
        UpdateCpeIdRequest updateRequest = new UpdateCpeIdRequest();
        WifiConfigurationType configuration = new WifiConfigurationType();
        configuration.setCpeId(request.getCpeId());
        configuration.setWifiBand(request.getWifiBand() != null ? WifiBandType.valueOf(request.getWifiBand()) : null);
        configuration.setSsid(request.getSsid());
        configuration.setEncryptionType(request.getEncryptionType() != null ? EncryptionType.valueOf(request.getEncryptionType()) : null);
        configuration.setPassword(request.getPassword());
        updateRequest.setConfiguration(configuration);

        UpdateCpeIdResponse response = this.soapClient.updateCpeId(updateRequest);
        this.wifiParametherCache.saveUpdated(response);

        PutWifiParametherResponse result = new PutWifiParametherResponse();
        WifiConfigurationType configurationResponse = response.getConfiguration();

        result.setCpeId(configurationResponse.getCpeId());
        result.setWifiBand(configurationResponse.getWifiBand().value());
        result.setSsid(configurationResponse.getSsid());
        result.setEncryptionType(configurationResponse.getEncryptionType().value());
        result.setPassword(configurationResponse.getPassword());

        return result;
    }
}
