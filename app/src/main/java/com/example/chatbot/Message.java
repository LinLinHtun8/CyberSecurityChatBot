package com.example.chatbot;

public class Message {
    static String Sent_By_User="user";
    static String Sent_By_Model = "model";
    public  static final String Typing = "Ai is replying...";

    private String message;
    String Sendby;

    public String getSent_By_User() {
        return Sent_By_User;
    }

    public void setSent_By_User(String sent_By_User) {
        Sent_By_User = sent_By_User;
    }

    public String getSent_By_Model() {
        return Sent_By_Model;
    }

    public void setSent_By_Model(String sent_By_Model) {
        Sent_By_Model = sent_By_Model;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendby() {
        return Sendby;
    }

    public void setSendby(String sendby) {
        Sendby = sendby;
    }

    public Message(String message, String sendby) {
        this.message = message;
        Sendby = sendby;
    }
}
