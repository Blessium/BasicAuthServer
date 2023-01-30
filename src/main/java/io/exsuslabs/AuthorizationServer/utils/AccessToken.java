package io.exsuslabs.AuthorizationServer.utils;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

public class AccessToken {
    private UUID clientId;
    private Instant issued_at;
    private Instant expires_at;
    private String username;

    public AccessToken(UUID clientId, TemporalAmount timeToExpire, String username) {
        this.clientId = clientId;
        this.issued_at = Instant.now();
        this.expires_at = issued_at.plus(timeToExpire);
        this.username = username;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public Instant getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Instant expires_at) {
        this.expires_at = expires_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isExpired() {
        return issued_at.isAfter(expires_at);
    }

    public boolean isEqual(String username, UUID clientId) {
        return (this.username.equals(username) && this.clientId.equals(clientId) );
    }
}