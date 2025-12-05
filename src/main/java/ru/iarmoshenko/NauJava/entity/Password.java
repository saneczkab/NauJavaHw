package ru.iarmoshenko.NauJava.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Passwords")
public class Password {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private byte[] encryptedPassword;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @ManyToOne
    @JoinColumn(name = "algorithm_id", nullable = false)
    private Algorithm algorithm;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Password() {}

    public Password(User user, byte[] encryptedPassword, Content content, Algorithm algorithm,
                    String salt, int length, LocalDateTime updatedAt) {
        setUser(user);
        setEncryptedPassword(encryptedPassword);
        setContent(content);
        setAlgorithm(algorithm);
        setSalt(salt);
        setLength(length);
        setUpdatedAt(updatedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return id == password.id && length == password.length && Objects.equals(user, password.user) && Objects.deepEquals(encryptedPassword, password.encryptedPassword) && Objects.equals(salt, password.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, Arrays.hashCode(encryptedPassword), content, algorithm, salt, length, updatedAt);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", encryptedPassword=" + Arrays.toString(encryptedPassword) +
                ", content=" + content.getName() +
                ", algorithm=" + algorithm.getName() +
                ", salt='" + salt + '\'' +
                ", length=" + length +
                ", updatedAt=" + updatedAt +
                "}<br/>";
    }
}
