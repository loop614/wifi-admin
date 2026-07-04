package com.tony.wrapper.wifiparamether;

import org.springframework.stereotype.Service;

import com.tony.wrapper.wifiparamether.domain.WifiParametherDispatcher;
import com.tony.wrapper.wifiparamether.dto.PutWifiParametherRequest;
import com.tony.wrapper.wifiparamether.dto.PutWifiParametherResponse;
import com.tony.wrapper.wifiparamether.dto.WifiParametherResponse;

@Service
public class WifiParametherConfigService {
    private final WifiParametherDispatcher cpeIdDispatcher;

    public WifiParametherConfigService(WifiParametherDispatcher cpeIdDispatcher) {
        this.cpeIdDispatcher = cpeIdDispatcher;
    }

    public WifiParametherResponse getCpeId(String cpeId) {
        return cpeIdDispatcher.getCpeId(cpeId);
    }

    public PutWifiParametherResponse updateCpeId(PutWifiParametherRequest request) {
        return cpeIdDispatcher.updateCpeId(request);
    }
}
