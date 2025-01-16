package ru.temnikov.demo.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClientServiceImpl implements ClientService {

    private final Map<String, UUID> ipUuidClientMap = new ConcurrentHashMap<>();

    public UUID createOrGetClient(final String ip) {
        if (ip == null) {
            return null;
        }
        ipUuidClientMap.putIfAbsent(ip, UUID.randomUUID());
        return ipUuidClientMap.get(ip);
    }

    public UUID getClientUUID(final String ip) {
        return ipUuidClientMap.get(ip);
    }

}
