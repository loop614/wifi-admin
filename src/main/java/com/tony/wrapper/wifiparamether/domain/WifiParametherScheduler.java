package com.tony.wrapper.wifiparamether.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tony.wrapper.wifiparamether.dto.WifiParametherResponse;
import com.tony.wrapper.wifiparamether.entity.WifiConfiguration;
import com.tony.wrapper.wifiparamether.repository.WifiConfigurationRepository;

@Component
public class WifiParametherScheduler {

    private static final List<String> CPE_IDS = Arrays.asList(
            "CPE_001", "CPE_002", "CPE_003", "CPE_004", "CPE_005", "CPE_006",
            "CPE_007", "CPE_008", "CPE_009", "CPE_010", "CPE_011", "CPE_012");

    private final WifiParametherDispatcher dispatcher;
    private final WifiParametherCache cache;

    private final WifiConfigurationRepository wifiConfigurationRepository;

    public WifiParametherScheduler(WifiParametherDispatcher dispatcher, WifiParametherCache wifiParametherCache, WifiConfigurationRepository wifiConfigurationRepository) {
        this.dispatcher = dispatcher;
        this.cache = wifiParametherCache;
        this.wifiConfigurationRepository = wifiConfigurationRepository;
    }

    @Scheduled(cron = "0 0 2 * * *", zone = "Europe/Zagreb")
    public void syncWifiConfiguration() {
        for (String cpeId : CPE_IDS) {
            Optional<WifiConfiguration> configuration = this.wifiConfigurationRepository.getByCpeId(cpeId);
            if (configuration.isPresent()) {
                continue;
            }
            WifiParametherResponse response = this.dispatcher.fetchFromSoap(cpeId);
            this.cache.save(response);
        }
    }
}
