package io.exsuslabs.AuthorizationServer.utils;

import java.util.UUID;

public class AccessToken {
    private UUID value;
    private Long user_id;

    public AccessToken() {}
    public AccessToken(UUID token, Long user_id) {
        value = token;
        this.user_id = user_id;
    }

    public UUID getValue() {
        return value;
    }

    public void setValue(UUID value) {
        this.value = value;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
