package ru.iarmoshenko.NauJava.entity;

import jakarta.persistence.*;

/**
 * Класс, представляющий сущность контента в системе.
 * Контент определяет набор символов, используемых при генерации паролей.
 * Отображается на таблицу "Contents" в базе данных.
 */
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

    @Enumerated(EnumType.STRING)  // Теперь правильно - для enum
    @Column(nullable = false)
    private ContentType contentType;

    protected Content() {
    }

    public Content(String name, String description, ContentType contentType) {
        setName(name);
        setDescription(description);
        setContentType(contentType);
        setUsedSymbols(contentType.getSymbols());
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

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
