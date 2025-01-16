package ru.temnikov.demo.entity;

import java.util.Date;
import java.util.UUID;

public record ShortUrl(UUID user, Date creationDate, String shortUrl, String fullUrl) {
}
