package ru.iarmoshenko.NauJava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Contents")
public class Content {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column()
    private String description;

    @Column(unique = true, nullable = false)
    private String usedSymbols;

    protected Content() {}

    public Content(String name, String description, String usedSymbols) {
        setName(name);
        setDescription(description);
        setUsedSymbols(usedSymbols);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsedSymbols() {
        return usedSymbols;
    }

    public void setUsedSymbols(String usedSymbols) {
        this.usedSymbols = usedSymbols;
    }
}
