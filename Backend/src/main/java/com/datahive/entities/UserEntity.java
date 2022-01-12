package com.datahive.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user", schema = "public", catalog = "DataHive")
public class UserEntity {
    private Long id;
    private String username;

    public UserEntity() {

    }

    public UserEntity(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
