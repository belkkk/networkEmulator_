package com.example.network.emulator.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_number")
    private Integer deviceNumber;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @ManyToOne(optional = false)
    @JoinColumn(name = "configuration_id", nullable = false)
    @JsonIgnore
    private NetworkConfiguration configuration;

    public Device(Long id, Integer deviceNumber, String ip, String type, int x, int y, NetworkConfiguration configuration) {
        this.id = id;
        this.deviceNumber = deviceNumber;
        this.ip = ip;
        this.type = type;
        this.x = x;
        this.y = y;
        this.configuration = configuration;
    }

    public Device(){}

    public Long getId() {
       return id;
   }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDeviceNumber(Integer deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getIp() {
        return ip;
    }

    public String getType() {
        return type;
    }

    public Integer getDeviceNumber() {
        return deviceNumber;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public NetworkConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(NetworkConfiguration configuration) {
        this.configuration = configuration;
    }
}
