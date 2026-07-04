package com.tony.wrapper.wifiparamether.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.tony.wrapper.wifiparamether.dto.PutWifiParametherRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class WifiParametherControllerTest {

    @Test
    void requestShouldFailValidationWhenRequiredFieldsAreMissing() {
        PutWifiParametherRequest request = new PutWifiParametherRequest();
        request.setCpeId("");
        request.setWifiBand("INVALID");
        request.setSsid("");
        request.setEncryptionType("INVALID");

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<PutWifiParametherRequest>> violations = validator.validate(request);

            assertFalse(violations.isEmpty());
            assertTrue(violations.stream().anyMatch(violation -> "cpeId".equals(violation.getPropertyPath().toString())));
            assertTrue(violations.stream().anyMatch(violation -> "wifiBand".equals(violation.getPropertyPath().toString())));
            assertTrue(violations.stream().anyMatch(violation -> "ssid".equals(violation.getPropertyPath().toString())));
            assertTrue(violations.stream().anyMatch(violation -> "encryptionType".equals(violation.getPropertyPath().toString())));
        }
    }
}
