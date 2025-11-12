package ru.iarmoshenko.NauJava.entity;

public enum LegacyContent {
    LETTERS("Только латинские буквы: A-Z, a-z"),
    DIGITS("Только цифры: 0-9"),
    SYMBOLS("Только спецсимволы"),
    LETTERS_DIGITS("Латинские буквы и цифры"),
    LETTERS_SYMBOLS("Латинские буквы и спецсимволы"),
    DIGITS_SYMBOLS("Цифры и спецсимволы"),
    MIXED("Латинские буквы, цифры и спецсимволы");

    private final String description;

    private LegacyContent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
