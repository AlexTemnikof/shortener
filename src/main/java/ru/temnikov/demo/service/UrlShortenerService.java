package ru.temnikov.demo.service;

import ru.temnikov.demo.entity.ShortUrl;

import java.util.List;
import java.util.UUID;

public interface UrlShortenerService {

    String shortenUrl(final String url, final UUID clientId);

    String redirectRequest(final String shortenUrl);

    List<ShortUrl> getUserShortLinks(final UUID clientId);
}
