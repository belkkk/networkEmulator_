package com.example.network.emulator.dto;

import com.example.network.emulator.models.Connection;
import com.example.network.emulator.models.Device;

import java.util.List;

public class CheckRequest {
    private List<Device> devices;

    private List<Connection> connections;

    private Long fromDeviceNumber;
    private Long toDeviceNumber;

    public CheckRequest(List<Device> devices, List<Connection> connections, Long fromDeviceNumber, Long toDeviceNumber) {
        this.devices = devices;
        this.connections = connections;
        this.fromDeviceNumber = fromDeviceNumber;
        this.toDeviceNumber = toDeviceNumber;
    }

    private CheckRequest(){}

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void setFromDeviceNumber(Long fromDeviceNumber) {
        this.fromDeviceNumber = fromDeviceNumber;
    }

    public void setToDeviceNumber(Long toDeviceNumber) {
        this.toDeviceNumber = toDeviceNumber;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Long getFromDeviceNumber() {
        return fromDeviceNumber;
    }

    public Long getToDeviceNumber() {
        return toDeviceNumber;
    }
}
