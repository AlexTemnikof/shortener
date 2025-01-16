package ru.temnikov.demo.service;

import java.util.UUID;

public interface ClientService {

    UUID createOrGetClient(final String ip);

    UUID getClientUUID(final String ip);
}
