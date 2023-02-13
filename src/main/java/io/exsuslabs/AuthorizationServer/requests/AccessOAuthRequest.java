package io.exsuslabs.AuthorizationServer.requests;

public class AccessOAuthRequest {
    private String client_id;
    private String password;
    private String request_id;

    public AccessOAuthRequest() {
    }

    public AccessOAuthRequest(String client_id, String password, String request_id) {
        this.client_id = client_id;
        this.password = password;
        this.request_id = request_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
