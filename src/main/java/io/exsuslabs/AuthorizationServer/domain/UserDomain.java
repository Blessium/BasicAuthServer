package io.exsuslabs.AuthorizationServer.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Table(name = "users")
@Entity
public class UserDomain implements Serializable {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DeveloperDomain developer;

    public UserDomain() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
