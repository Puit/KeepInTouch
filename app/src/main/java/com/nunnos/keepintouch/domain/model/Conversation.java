package com.nunnos.keepintouch.domain.model;

import com.nunnos.keepintouch.data.entities.conversation.ConversationEntity;

public class Conversation {
    private int id;
    private Long date;
    private String chat;
    private String place;
    private boolean isImportant;
    private String contacts;

    public Conversation(ConversationEntity entity) {
        this.setId(entity.id);
        this.setChat(entity.chat);
        this.setDate(entity.date);
        this.setPlace(entity.place);
        this.setImportant(entity.isImportant);
        this.setContacts(entity.contacts);
    }

    /**
     * GETTERS AND SETTERS
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
