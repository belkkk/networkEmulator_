package com.example.network.emulator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "connections")
@Data
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_device_id", nullable = false)
    private Device fromDeviceId;

    @ManyToOne
    @JoinColumn(name = "to_device_id", nullable = false)
    private Device toDeviceId;

    @ManyToOne
    @JoinColumn(name = "configuration_id")
    @JsonIgnore
    private NetworkConfiguration networkConfiguration;

    public NetworkConfiguration getNetworkConfiguration() {
        return networkConfiguration;
    }

    public Long getId() {
        return id;
    }

    public Device getFromDeviceId() {
        return fromDeviceId;
    }

    public Device getToDeviceId() {
        return toDeviceId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFromDeviceId(Device fromDeviceId) {
        this.fromDeviceId = fromDeviceId;
    }

    public void setToDeviceId(Device toDeviceId) {
        this.toDeviceId = toDeviceId;
    }

    public void setConfiguration(NetworkConfiguration networkConfiguration) {
        this.networkConfiguration = networkConfiguration;
    }
}
