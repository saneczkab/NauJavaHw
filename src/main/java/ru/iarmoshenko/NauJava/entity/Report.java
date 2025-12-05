package ru.iarmoshenko.NauJava.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Reports")
public class Report {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.CREATED;

    @Column(columnDefinition = "TEXT")
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
