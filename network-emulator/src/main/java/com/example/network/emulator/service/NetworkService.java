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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        for(Device device : request.getDevices()){
            device.setId(device.getDeviceNumber().longValue());
        }

        List<Long> path = bfsService.findPath(
                request.getConnections(),
                request.getDevices(),
                request.getFromDeviceNumber(),
                request.getToDeviceNumber()
        );

        return new CheckResponse(path);
    }

    @Transactional
    public ConfigurationInfoResponse saveConfiguration(SaveConfigurationRequest request) {
        NetworkConfiguration config = new NetworkConfiguration();
        config.setName(request.getName());
        config = configurationRepository.save(config);


        Map<Integer, Device> deviceByNumber = new HashMap<>();

        for (Device device : request.getDevices()) {
            Integer deviceNumber = device.getDeviceNumber();
            device.setId(null);                     // обнуляем глобальный ID
            device.setConfiguration(config);
            Device savedDevice = deviceRepository.save(device);
            deviceByNumber.put(deviceNumber, savedDevice);
        }

        for (Connection connection : request.getConnections()) {
            // Клиент прислал fromDevice и toDevice с заполненным deviceNumber
            Integer fromNumber = connection.getFromDevice().getDeviceNumber();
            Integer toNumber = connection.getToDevice().getDeviceNumber();

            Device savedFrom = deviceByNumber.get(fromNumber);
            Device savedTo = deviceByNumber.get(toNumber);

            connection.setId(null);
            connection.setFromDevice(savedFrom);
            connection.setToDevice(savedTo);
            connection.setConfiguration(config);
        }
        connectionRepository.saveAll(request.getConnections());

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