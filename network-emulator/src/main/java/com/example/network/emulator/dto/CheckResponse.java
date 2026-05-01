package com.example.network.emulator.dto;

import java.util.List;
import java.util.stream.Collectors;

public class CheckResponse {
    private List<String> path;

    public CheckResponse(List<Long> path) {
        this.path = path.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    public List<String> getPath() {
        return path;
    }
}