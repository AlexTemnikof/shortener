package ru.temnikov.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.temnikov.demo.entity.ShortUrl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final Map<String, ShortUrl> urlToShortUrlMap = new ConcurrentHashMap<>();

    private final Random random = new Random();

    private final static String prefix = "click/";

    private final long lifetime;

    public UrlShortenerServiceImpl(@Value("${shortenapp.lifetime}") final long lifetime) {
        this.lifetime = lifetime;
    }


    public String shortenUrl(final String url, final Long limit, final UUID clientUUID) {
        final String shortCode = generateShortCode();
        final ShortUrl shortUrlEntity = new ShortUrl(clientUUID, new Date(), shortCode, url, limit, 0L);
        urlToShortUrlMap.put(shortCode, shortUrlEntity);
        return prefix + shortCode;
    }

    public String redirectRequest(final String shortenUrl) {
        if (shortenUrl == null || !shortenUrl.startsWith(prefix)) {
            throw new UnsupportedOperationException("Unsupported redirection link");
        };

        final String shortCode = shortenUrl.substring(prefix.length());
        final ShortUrl shortUrl = urlToShortUrlMap.get(shortCode);
        if (shortUrl.limit() > shortUrl.used()) {
            throw new RuntimeException("the limit is over");
        }
        shortUrl.incrementUsed();
        return shortUrl.fullUrl();
    }

    private String generateShortCode() {
        return Integer.toHexString(random.nextInt()).toUpperCase();
    }

    public List<ShortUrl> getUserShortLinks(final UUID clientId) {
        return urlToShortUrlMap.values().stream()
                .filter(shortUrl -> shortUrl.user().equals(clientId))
                .collect(Collectors.toList());
    }

    /**
     * Если на проде, то здесь узкое место, но для пет проекта с ин мемори норм
     */
    @Scheduled
    private void deleteOldLinks() {
        long currentTime = System.currentTimeMillis();
        urlToShortUrlMap.entrySet().removeIf(entry ->
                currentTime - entry.getValue().creationDate().getTime() > lifetime
        );
    }
}
