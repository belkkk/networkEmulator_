package com.example.network.emulator.service;

import com.example.network.emulator.models.Connection;
import com.example.network.emulator.models.Device;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BfsService {

    public List<Long> findPath(List<Connection> connections,
                               List<Device> devices,
                               Long fromId,
                               Long toId) {
        if (fromId.equals(toId)) {
            return Arrays.asList(fromId);
        }

        // Карта ID -> IP
        Map<Long, String> ipMap = new HashMap<>();
        for (Device device : devices) {
            ipMap.put(device.getId(), device.getIp());
        }

        // Неориентированный граф
        Map<Long, Set<Long>> graph = new HashMap<>();
        for (Connection conn : connections) {
            Long from = conn.getFromDeviceId().getId();
            Long to = conn.getToDeviceId().getId();
            graph.computeIfAbsent(from, k -> new HashSet<>()).add(to);
            graph.computeIfAbsent(to, k -> new HashSet<>()).add(from);
        }

        // BFS с проверкой IP
        Queue<BfsState> queue = new LinkedList<>();
        Set<String> startIps = new HashSet<>();
        String startIp = ipMap.get(fromId);
        if (startIp != null) startIps.add(startIp);

        List<Long> startPath = new ArrayList<>();
        startPath.add(fromId);
        queue.add(new BfsState(startPath, startIps));

        Set<Long> visited = new HashSet<>();
        visited.add(fromId);

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

                if (neighbor.equals(toId)) {
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