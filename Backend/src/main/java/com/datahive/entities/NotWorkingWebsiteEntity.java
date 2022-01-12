package com.datahive.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "not_working_website", schema = "public", catalog = "DataHive")
public class NotWorkingWebsiteEntity {
    private Long id;
    private String url;
    private Timestamp date;

    public NotWorkingWebsiteEntity() {
    }

    public NotWorkingWebsiteEntity(String url, Timestamp date){
        this.url = url;
        this.date = date;
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
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
}
