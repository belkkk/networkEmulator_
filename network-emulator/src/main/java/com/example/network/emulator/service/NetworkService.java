package com.example.network.emulator.service;

import com.example.network.emulator.dto.*;
import com.example.network.emulator.models.Connection;
import com.example.network.emulator.models.Device;
import com.example.network.emulator.models.NetworkConfiguration;
import com.example.network.emulator.repositories.ConnectionRepository;
import com.example.network.emulator.repositories.DeviceRepository;
import com.example.network.emulator.repositories.NetworkConfigurationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NetworkService {

    private final ConnectionRepository connectionRepository;
    private final DeviceRepository deviceRepository;
    private final NetworkConfigurationRepository configurationRepository;
    private final BfsService bfsService;

    public NetworkService(ConnectionRepository connectionRepository, DeviceRepository deviceRepository, NetworkConfigurationRepository configurationRepository, BfsService bfsService) {
        this.connectionRepository = connectionRepository;
        this.deviceRepository = deviceRepository;
        this.configurationRepository = configurationRepository;
        this.bfsService = bfsService;
    }

    public CheckResponse checkConnectivity(CheckRequest request) {

        List<Long> path = bfsService.findPath(
                request.getConnections(),
                request.getDevices(),
                request.getFromDeviceId(),
                request.getToDeviceId()
        );

        return new CheckResponse(path);
    }

    @Transactional
    public ConfigurationInfoResponse saveConfiguration(SaveConfigurationRequest request) {
        NetworkConfiguration config = new NetworkConfiguration();
        config.setName(request.getName());
        config = configurationRepository.save(config);  // сохраняем конфигурацию сразу

        // Явно сохраняем устройства (без каскада от конфигурации)
        for (Device device : request.getDevices()) {
            device.setId(null);
            device.setConfiguration(config);
        }
        deviceRepository.saveAll(request.getDevices());  // ← явный вызов
        // Теперь у всех устройств есть реальные ID в базе

        // Сохраняем соединения
        for (Connection connection : request.getConnections()) {
            connection.setId(null);
            connection.setConfiguration(config);
        }
        connectionRepository.saveAll(request.getConnections());  // ← явный вызов

        // Не вызываем config.setDevices() и config.setConnections() —
        // пусть каскад не пытается сохранить всё сам

        return new ConfigurationInfoResponse(config.getId(), config.getName());
    }

    public List<ConfigurationInfoResponse> getAllConfigurations(){
        return configurationRepository.findAll()
                .stream()
                .map(c -> new ConfigurationInfoResponse(c.getId(),c.getName()))
                .collect(Collectors.toList());
    }

    public ConfigurationResponse getOneConfiguration(Long configId){
        NetworkConfiguration config = configurationRepository.findById(configId)
                .orElseThrow(() -> new RuntimeException("Configuration not found"));
        return new ConfigurationResponse(config.getDevices(),config.getConnections());
    }

    @Transactional
    public void deleteConfiguration(Long configId){
        configurationRepository.deleteById(configId);
    }

}