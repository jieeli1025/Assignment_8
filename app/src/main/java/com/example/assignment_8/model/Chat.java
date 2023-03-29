package com.example.assignment_8.model;

import java.io.Serializable;

public class Chat implements Serializable {
    private String chatText;

    public Chat() {
    }

    public Chat(String chatText) {
        this.chatText = chatText;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String toString(){
        return chatText;
    }
}
