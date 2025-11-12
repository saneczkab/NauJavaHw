package ru.iarmoshenko.NauJava.entity;

import java.util.Date;

public class LegacyPassword {
    private Long id;
    private Long userId;
    private byte[] encryptedPassword;
    private LegacyContent content;
    private String salt;
    private int length;
    private Date updatedAt;

    public LegacyPassword(Long id, Long userId, byte[] encryptedPassword, LegacyContent content, String salt, int length) {
        setId(id);
        setUserId(userId);
        setEncryptedPassword(encryptedPassword);
        setContent(content);
        setSalt(salt);
        setLength(length);
        setUpdatedAt(new Date());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public LegacyContent getContent() {
        return content;
    }

    public void setContent(LegacyContent content) {
        this.content = content;
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
        if (length < 1) {
            throw new IllegalArgumentException("Password length must be at least 1");
        }

        this.length = length;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
