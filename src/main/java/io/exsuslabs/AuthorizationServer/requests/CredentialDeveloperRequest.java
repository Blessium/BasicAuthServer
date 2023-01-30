package io.exsuslabs.AuthorizationServer.requests;

public class CredentialDeveloperRequest {
    private String client_id;
    private String password;

    public CredentialDeveloperRequest() {
    }

    public CredentialDeveloperRequest(String client_id, String passoword) {
        this.client_id = client_id;
        this.password = passoword;
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
}
