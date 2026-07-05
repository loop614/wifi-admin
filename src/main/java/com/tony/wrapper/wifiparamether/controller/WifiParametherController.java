package com.tony.wrapper.wifiparamether.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tony.wrapper.wifiparamether.WifiParametherConfigService;
import com.tony.wrapper.wifiparamether.dto.PutWifiParametherRequest;
import com.tony.wrapper.wifiparamether.dto.PutWifiParametherResponse;
import com.tony.wrapper.wifiparamether.dto.WifiParametherResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@Validated
public class WifiParametherController {
    private final WifiParametherConfigService wifiParametherConfigService;

    public WifiParametherController(WifiParametherConfigService wifiParametherConfigService) {
        this.wifiParametherConfigService = wifiParametherConfigService;
    }

    @GetMapping("/wifi-parameter/{cpeId}")
    public ResponseEntity<WifiParametherResponse> getWifiParameter(@PathVariable @NotBlank String cpeId) {
        WifiParametherResponse response = this.wifiParametherConfigService.getCpeId(cpeId);
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No wifi configuration found for cpeId: " + cpeId);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/wifi-parameter")
    public ResponseEntity<PutWifiParametherResponse> putWifiParameter(@Valid @RequestBody PutWifiParametherRequest request) {
        return ResponseEntity.ok(this.wifiParametherConfigService.updateCpeId(request));
    }
}
