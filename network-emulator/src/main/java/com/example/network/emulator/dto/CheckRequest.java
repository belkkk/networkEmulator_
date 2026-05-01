package com.example.network.emulator.dto;

import com.example.network.emulator.models.Connection;
import com.example.network.emulator.models.Device;

import java.util.List;

public class CheckRequest {
    private List<Device> devices;

    private List<Connection> connections;

    private Long fromDeviceId;
    private Long toDeviceId;

    public CheckRequest(List<Device> devices, List<Connection> connections, Long fromDeviceId, Long toDeviceId) {
        this.devices = devices;
        this.connections = connections;
        this.fromDeviceId = fromDeviceId;
        this.toDeviceId = toDeviceId;
    }


    public List<Device> getDevices() {
        return devices;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Long getFromDeviceId() {
        return fromDeviceId;
    }

    public Long getToDeviceId() {
        return toDeviceId;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public void setFromDeviceId(Long fromDeviceId) {
        this.fromDeviceId = fromDeviceId;
    }

    public void setToDeviceId(Long toDeviceId) {
        this.toDeviceId = toDeviceId;
    }
}
