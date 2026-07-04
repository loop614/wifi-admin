package com.tony.wrapper.soapClient;

import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import local.wifi_admin.platform.v1.GetCpeIdRequest;
import local.wifi_admin.platform.v1.GetCpeIdResponse;
import local.wifi_admin.platform.v1.UpdateCpeIdRequest;
import local.wifi_admin.platform.v1.UpdateCpeIdResponse;

@Service
public class SoapClientService {
    private static final String ENDPOINT = "http://localhost:8080/platform";

    private final WebServiceTemplate webServiceTemplate;

    public SoapClientService(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public GetCpeIdResponse getCpeId(GetCpeIdRequest request) {
        return (GetCpeIdResponse)
            webServiceTemplate.marshalSendAndReceive(
                ENDPOINT,
                request,
                new SoapActionCallback("http://wifi-admin.local/platform/v1#getCpeID")
            );
    }

    public UpdateCpeIdResponse updateCpeId(UpdateCpeIdRequest request) {
        return (UpdateCpeIdResponse)
            webServiceTemplate.marshalSendAndReceive(
                ENDPOINT,
                request,
                new SoapActionCallback("http://wifi-admin.local/platform/v1#updateCpeID")
        );
    }
}
