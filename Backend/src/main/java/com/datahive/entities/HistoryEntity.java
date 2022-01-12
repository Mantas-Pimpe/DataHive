package com.datahive.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "history", schema = "public", catalog = "DataHive")
public class HistoryEntity {
    private Long id;
    private String url;
    private Long userId;
    private String cmp;
    private Timestamp date;

    public HistoryEntity() {
    }

    public HistoryEntity(String url, String cmp, Timestamp date, Long userId){
        this.url = url;
        this.cmp = cmp;
        this.date = date;
        this.userId = userId;
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
    @Column(name = "url")
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "cmp")
    public String getCmp() {
        return cmp;
    }
    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
}
