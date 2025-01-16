package ru.temnikov.demo.entity;

import java.util.Date;
import java.util.UUID;

public class ShortUrl{

    private UUID user;

    private Date creationDate;

    private String shortUrl;

    private String fullUrl;

    private Long limit;

    private Long used;

    public ShortUrl( final UUID user, final Date creationDate, final String shortUrl,
                        final String fullUrl, final Long limit, final Long used) {
        this.user = user;
        this.creationDate = creationDate;
        this.shortUrl = shortUrl;
        this.fullUrl = fullUrl;
        this.limit = limit;
        this.used = used;
    }

    public UUID user() {
        return user;
    }

    public void setUser(final UUID user) {
        this.user = user;
    }

    public Date creationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public String shortUrl() {
        return shortUrl;
    }

    public void setShortUrl(final String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String fullUrl() {
        return fullUrl;
    }

    public void setFullUrl(final String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public Long limit() {
        return limit;
    }

    public void setLimit(final Long limit) {
        this.limit = limit;
    }

    public Long used() {
        return used;
    }

    public void incrementUsed() {
        this.used += 1;
    }
}
