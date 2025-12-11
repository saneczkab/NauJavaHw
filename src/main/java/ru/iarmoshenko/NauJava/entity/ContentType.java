package ru.iarmoshenko.NauJava.entity;

public enum ContentType {
    LETTERS("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    DIGITS("0123456789"),
    SPECIAL_CHARS("!@#$%^&*()_-+={[]};:'\",<.>/?\\|~`"),
    LETTERS_DIGITS("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"),
    LETTERS_SPECIAL_CHARS("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_-+={[]};:'\",<.>/?\\|~`"),
    DIGITS_SPECIAL_CHARS("0123456789!@#$%^&*()_-+={[]};:'\",<.>/?\\|~`"),
    MIX("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-+={[]};:'\",<.>/?\\|~`");

    private final String symbols;

    ContentType(String symbols) {
        this.symbols = symbols;
    }

    public String getSymbols() {
        return symbols;
    }

    public char[] getSymbolsAsCharArray() {
        return symbols.toCharArray();
    }
}
