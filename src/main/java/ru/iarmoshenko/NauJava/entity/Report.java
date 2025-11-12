package ru.iarmoshenko.NauJava.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Reports")
public class Report {
    @Id
    @GeneratedValue
    public int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ReportStatus status = ReportStatus.CREATED;

    @Column
    public String content;
}
