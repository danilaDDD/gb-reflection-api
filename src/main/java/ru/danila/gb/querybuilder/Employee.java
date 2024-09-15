package ru.danila.gb.querybuilder;

import java.util.UUID;

@Entity
@Table(name = "users")
public class Employee {
    @Column(name = "id", primaryKey = true)
    private UUID id;

    @Column(name = "username", primaryKey = false)
    private String username;

    @Column(name = "email", primaryKey = false)
    private String email;

    public Employee(String username, String email) {
        this.email = email;
        this.username = username;
        this.id = UUID.randomUUID();
    }

    public String getEmail() {
        return email;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
