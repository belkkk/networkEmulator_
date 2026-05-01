package com.example.network.emulator.controllers;

import com.example.network.emulator.dto.*;
import com.example.network.emulator.service.NetworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/network")
@CrossOrigin(origins = "http://localhost:4200")
public class NetworkController {

    private NetworkService networkService;

    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @PostMapping("/test")
    public String test(){
        return "it works";
    }

    @PostMapping("/check")
    public CheckResponse emulate(@RequestBody CheckRequest request){
        return networkService.checkConnectivity(request);
    }

    @PostMapping("/save")
   public ConfigurationInfoResponse save(@RequestBody SaveConfigurationRequest request){
        return networkService.saveConfiguration(request);
    }

    @GetMapping("configurations")
    public List<ConfigurationInfoResponse> getAll(){
        return networkService.getAllConfigurations();
    }

    @GetMapping("/configurations/{id}")
    public ConfigurationResponse getOne(@PathVariable Long id) {
        return networkService.getOneConfiguration(id);
    }

    @DeleteMapping("/configurations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        networkService.deleteConfiguration(id);
            return ResponseEntity.noContent().build();
        }


}
