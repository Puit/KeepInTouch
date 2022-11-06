package com.nunnos.keepintouch.domain.model.complements;

import static com.nunnos.keepintouch.utils.Constants.CONTACTS_SEPARATOR;

import com.nunnos.keepintouch.data.entities.conversation.ConversationEntity;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.domain.model.complements.base.Complement;
import com.nunnos.keepintouch.utils.TextUtils;

import java.util.List;

public class Conversation extends Complement {

    private String time;
    private String chat;
    private String place;
    private boolean isImportant;
    private String photo;
    private float angle;
    private String contacts = CONTACTS_SEPARATOR;

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
        contacts = contacts + contactId + CONTACTS_SEPARATOR;
    }

    public void addContactList(List<Contact> contactList) {
        for (Contact c : contactList) {
            addContact(c.getId());
        }
    }

    public void removeContact(int contactId) {
        if (!containsContact(contactId)) return;
        contacts.replace(CONTACTS_SEPARATOR + contactId + CONTACTS_SEPARATOR, CONTACTS_SEPARATOR);
        contacts = contacts + contactId + CONTACTS_SEPARATOR;
    }

    public boolean containsContact(int contactId) {
        return contacts.contains(CONTACTS_SEPARATOR + contactId + CONTACTS_SEPARATOR);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public boolean isEmpty() {
        return !((getId() > 0) ||
                (photo != null) ||
                (!TextUtils.isNullOrEmpty(getDate())) ||
                (time != null) ||
                (chat != null) ||
                (place != null));
    }
}
