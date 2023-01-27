package io.exsuslabs.AuthorizationServer.requests;

import com.google.common.base.Objects;

public class DeveloperUpdateRequest {
    private String password;
    private String[] valid_urls;

    public DeveloperUpdateRequest(){ }

    public DeveloperUpdateRequest(String password, String[] valid_urls) {
        this.password = password;
        this.valid_urls = valid_urls;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getValid_urls() {
        return valid_urls;
    }

    public void setValid_urls(String[] valid_urls) {
        this.valid_urls = valid_urls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperUpdateRequest that = (DeveloperUpdateRequest) o;
        return Objects.equal(password, that.password) && Objects.equal(valid_urls, that.valid_urls);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password, valid_urls);
    }
}
