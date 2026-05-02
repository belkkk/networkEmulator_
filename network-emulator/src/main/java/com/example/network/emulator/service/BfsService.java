package com.example.network.emulator.service;

import com.example.network.emulator.models.Connection;
import com.example.network.emulator.models.Device;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BfsService {

    public List<Long> findPath(List<Connection> connections,
                               List<Device> devices,
                               Long fromDeviceNumber,
                               Long toDeviceNumber) {
        if (fromDeviceNumber.equals(toDeviceNumber)) {
            return Arrays.asList(fromDeviceNumber);
        }

        // Карта: deviceNumber -> IP
        Map<Long, String> ipMap = new HashMap<>();
        for (Device device : devices) {
            ipMap.put(device.getDeviceNumber().longValue(), device.getIp());
        }

        // Граф по deviceNumber
        Map<Long, Set<Long>> graph = new HashMap<>();
        for (Connection conn : connections) {
            Long from = conn.getFromDevice().getDeviceNumber().longValue();
            Long to = conn.getToDevice().getDeviceNumber().longValue();
            graph.computeIfAbsent(from, k -> new HashSet<>()).add(to);
            graph.computeIfAbsent(to, k -> new HashSet<>()).add(from);
        }

        Queue<BfsState> queue = new LinkedList<>();
        Set<String> startIps = new HashSet<>();
        String startIp = ipMap.get(fromDeviceNumber);
        if (startIp != null) startIps.add(startIp);

        List<Long> startPath = new ArrayList<>();
        startPath.add(fromDeviceNumber);
        queue.add(new BfsState(startPath, startIps));

        Set<Long> visited = new HashSet<>();
        visited.add(fromDeviceNumber);

        while (!queue.isEmpty()) {
            BfsState current = queue.poll();
            Long lastNode = current.path.get(current.path.size() - 1);
            Set<Long> neighbors = graph.getOrDefault(lastNode, new HashSet<>());

            for (Long neighbor : neighbors) {
                String neighborIp = ipMap.get(neighbor);

                if (neighborIp != null && current.usedIps.contains(neighborIp)) {
                    continue;
                }

                List<Long> newPath = new ArrayList<>(current.path);
                newPath.add(neighbor);

                if (neighbor.equals(toDeviceNumber)) {
                    return newPath;
                }

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    Set<String> newIps = new HashSet<>(current.usedIps);
                    if (neighborIp != null) {
                        newIps.add(neighborIp);
                    }
                    queue.add(new BfsState(newPath, newIps));
                }
            }
        }

        return Collections.emptyList();
    }

    private static class BfsState {
        List<Long> path;
        Set<String> usedIps;

        BfsState(List<Long> path, Set<String> usedIps) {
            this.path = path;
            this.usedIps = usedIps;
        }
    }
}