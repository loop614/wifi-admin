package com.tony.wrapper.wifiparamether.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tony.wrapper.wifiparamether.entity.WifiConfiguration;

@Repository
public interface WifiConfigurationRepository extends JpaRepository<WifiConfiguration, Long> {

    Optional<WifiConfiguration> getByCpeId(String cpeId);
}
