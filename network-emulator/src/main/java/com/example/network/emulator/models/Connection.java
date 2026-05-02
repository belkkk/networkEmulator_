package com.example.network.emulator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "connections")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "from_device_id", nullable = false)
    @JsonIgnoreProperties({"configuration"})
    private Device fromDevice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "to_device_id", nullable = false)
    @JsonIgnoreProperties({"configuration"})
    private Device toDevice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "configuration_id", nullable = false)
    @JsonIgnore
    private NetworkConfiguration configuration;

    public Connection() {}

    public Long getId() { return id; }
    public Device getFromDevice() { return fromDevice; }
    public Device getToDevice() { return toDevice; }
    public NetworkConfiguration getConfiguration() { return configuration; }

    public void setId(Long id) { this.id = id; }
    public void setFromDevice(Device fromDevice) { this.fromDevice = fromDevice; }
    public void setToDevice(Device toDevice) { this.toDevice = toDevice; }
    public void setConfiguration(NetworkConfiguration configuration) { this.configuration = configuration; }
}