package com.example.micha.androidlabs;

public class Message {
    String text;
    long id;
    boolean isSent;
    boolean isReceived;

    public Message( String te, long id, boolean iS, boolean iR) {
        this.text = te;
        this.id = id;
        this.isSent = iS;
        this.isReceived = iR;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public boolean getIsSent() {
        return isSent;
    }

    public boolean getIsReceived() {
        return isReceived;
    }
}
