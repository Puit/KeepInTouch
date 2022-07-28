package com.nunnos.keepintouch.domain.model;

import com.nunnos.keepintouch.data.entities.conversation.ConversationEntity;

public class Conversation {
    public static final String SEPARATOR = ",";
    private int id;
    private String date;
    private String time;
    private String chat;
    private String place;
    private boolean isImportant;
    private String photo;
    private float angle;
    private String contacts = SEPARATOR;

    public Conversation() {
        //Required
    }

    public Conversation(ConversationEntity entity) {
        this.setId(entity.id);
        this.setChat(entity.chat);
        this.setDate(entity.date);
        this.setTime(entity.time);
        this.setPlace(entity.place);
        this.setImportant(entity.isImportant);
        this.setPhoto(entity.photo);
        this.setAngle(entity.angle);
        addContactsFirstTime(entity.contacts);
    }

    public static Conversation map(ConversationEntity entity) {
        return new Conversation(entity);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void addContact(int contactId) {
        if (containsContact(contactId)) return;
        contacts = contacts + contactId + SEPARATOR;
    }

    public void removeContact(int contactId) {
        if (!containsContact(contactId)) return;
        contacts.replace(SEPARATOR + contactId + SEPARATOR, SEPARATOR);
        contacts = contacts + contactId + SEPARATOR;
    }

    public boolean containsContact(int contactId) {
        return contacts.contains(SEPARATOR + contactId + SEPARATOR);
    }

    private void addContactsFirstTime(String contacts) {
        if (contacts.startsWith(SEPARATOR)) {
            this.contacts = contacts;
        } else {
            this.contacts = SEPARATOR + contacts;
        }
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
