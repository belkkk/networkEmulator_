package com.example.network.emulator.dto;

import com.example.network.emulator.models.Connection;
import com.example.network.emulator.models.Device;

import java.util.List;

public class ConfigurationResponse {
    private List<Device> devices;
    private List<Connection> connections;

    public ConfigurationResponse(List<Device> devices, List<Connection> connections) {
        this.devices = devices;
        this.connections = connections;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
