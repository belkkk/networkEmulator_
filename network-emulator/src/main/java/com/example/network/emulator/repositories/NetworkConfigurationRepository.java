package com.example.network.emulator.repositories;

import com.example.network.emulator.models.NetworkConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkConfigurationRepository extends JpaRepository<NetworkConfiguration, Long> {
}
