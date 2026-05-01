package com.example.network.emulator.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "devices")
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @ManyToOne
    @JoinColumn(name = "configuration_id")
    @JsonIgnore
    private NetworkConfiguration configuration;

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

    public String getIp() {
        return ip;
    }

    public String getType() {
        return type;
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
