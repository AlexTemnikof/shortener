package ru.temnikov.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ru.temnikov.demo.entity.ShortUrl;
import ru.temnikov.demo.service.ClientService;
import ru.temnikov.demo.service.UrlShortenerService;

import java.util.List;
import java.util.UUID;

import static ru.temnikov.demo.util.Util.uncheck;

@RestController
@RequestMapping("/api/v1")
public class ShortenerController {

    private final ClientService clientService;

    private final UrlShortenerService urlShortenerService;

    public ShortenerController(final ClientService clientService, final UrlShortenerService urlShortenerService) {
        this.clientService = clientService;
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/{url}/{limit}")
    public String shortenUrl(HttpServletRequest request, @PathVariable final String url, @PathVariable(required = false) Long limit) {
        final String clientIp = request.getHeader("X-Forwarded-For");
        final UUID clientUUID = clientService.createOrGetClient(clientIp);
        return urlShortenerService.shortenUrl(url, limit, clientUUID);
    }

    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable final String shortUrl, final HttpServletResponse response) {
        final String fullUrl = urlShortenerService.redirectRequest(shortUrl);
        uncheck(() -> response.sendRedirect(fullUrl));
    }


    @GetMapping("/client/{userUUID}")
    public List<ShortUrl> getShortUrls(@PathVariable @NonNull final UUID userUUID) {
        return urlShortenerService.getUserShortLinks(userUUID);
    }
}
