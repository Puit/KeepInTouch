package com.nunnos.keepintouch.data.entities.conversation;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nunnos.keepintouch.domain.model.Conversation;

import static com.nunnos.keepintouch.data.entities.conversation.ConversationDaoConstants.TABLE_NAME;
import static com.nunnos.keepintouch.domain.model.Conversation.SEPARATOR;

@Entity(tableName = TABLE_NAME)
public class ConversationEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "chat")
    public String chat;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "isimportant")
    public boolean isImportant;

    @ColumnInfo(name = "photo")
    public String photo;

    @ColumnInfo(name = "contacts")
    public String contacts; //Seran los Ids de los implicados en la conversacion

    public ConversationEntity(int id, String date, String time, String chat, String place, boolean isImportant, String contacts, String photo) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.chat = chat;
        this.place = place;
        this.isImportant = isImportant;
        this.photo = photo;
        addContacts(contacts);
    }

    public ConversationEntity(String date, String time, String chat, String place, boolean isImportant, String contacts, String photo) {
        this.date = date;
        this.time = time;
        this.chat = chat;
        this.place = place;
        this.isImportant = isImportant;
        this.photo = photo;
        addContacts(contacts);
    }

    public ConversationEntity() {
        this.id = -2;
        this.date = "";
        this.time = "";
        this.chat = "";
        this.place = "";
        this.isImportant = false;
        this.contacts = SEPARATOR;
        this.photo = "";
    }

    public static ConversationEntity map(Conversation c) {
        if (c.getId() != 0) {
            return new ConversationEntity(c.getId(), c.getDate(), c.getTime(), c.getChat(), c.getPlace(), c.isImportant(), c.getContacts(), c.getPhoto());
        } else {
            return new ConversationEntity(c.getDate(), c.getTime(), c.getChat(), c.getPlace(), c.isImportant(), c.getContacts(), c.getPhoto());
        }
    }

    private void addContacts(String contacts) {
        if (contacts.startsWith(SEPARATOR)) {
            this.contacts = contacts;
        } else {
            this.contacts = SEPARATOR + contacts;
        }
    }
}
