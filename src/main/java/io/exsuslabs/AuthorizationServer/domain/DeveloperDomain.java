package io.exsuslabs.AuthorizationServer.domain;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Table(name="developers")
@Entity
public class DeveloperDomain implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "password")
    private String password;
    @Type(ListArrayType.class)
    @Column(
            name = "valid_urls",
            columnDefinition = "text[]"
    )
    private List<String> valid_urls;

    @OneToOne
    @JoinColumn(name = "username")
    private UserDomain user;

    public DeveloperDomain() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getValid_urls() {
        return valid_urls;
    }

    public void setValid_urls(List<String> valid_urls) {
        this.valid_urls = valid_urls;
    }

    public UserDomain getUser() {
        return user;
    }

    public void setUser(UserDomain user) {
        this.user = user;
    }
}
