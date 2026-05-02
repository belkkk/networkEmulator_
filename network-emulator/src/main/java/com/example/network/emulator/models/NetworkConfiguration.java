package com.example.network.emulator.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "network_configuration")
public class NetworkConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"configuration"})
    private List<Device> devices = new ArrayList<>();

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"configuration"})
    private List<Connection> connections = new ArrayList<>();

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Device> getDevices() { return devices; }
    public List<Connection> getConnections() { return connections; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDevices(List<Device> devices) { this.devices = devices; }
    public void setConnections(List<Connection> connections) { this.connections = connections; }
}