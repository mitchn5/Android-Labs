package com.example.micha.androidlabs;

public class Message {
    char type;
    String text;

    public Message(char ty, String te) {
        this.type = ty;
        this.text = te;
    }

    public String getText() {
        return text;
    }

    public char getType() {
        return type;
    }

}
