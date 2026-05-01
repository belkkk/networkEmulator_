package com.example.network.emulator.dto;

import com.example.network.emulator.models.Connection;
import com.example.network.emulator.models.Device;

import java.util.List;

public class SaveConfigurationRequest {
    private String name;
    private List<Device> devices;
    private List<Connection> connections;

    public String getName() {
        return name;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
