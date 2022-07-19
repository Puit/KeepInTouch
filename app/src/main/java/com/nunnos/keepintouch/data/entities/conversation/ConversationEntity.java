package com.nunnos.keepintouch.data.entities.conversation;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.nunnos.keepintouch.data.entities.conversation.ConversationDaoConstants.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class ConversationEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "date")
    public Long date;

    @ColumnInfo(name = "chat")
    public String chat;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "isimportant")
    public boolean isImportant;

    @ColumnInfo(name = "contacts")
    public String contacts; //Seran los Ids de los implicados en la conversacion

    public ConversationEntity(int id, Long date, String chat, String place, boolean isImportant, String contacts) {
        this.id = id;
        this.date = date;
        this.chat = chat;
        this.place = place;
        this.isImportant = isImportant;
        this.contacts = contacts;
    }

    public ConversationEntity() {
        this.id = -2;
        this.date = 0L;
        this.chat = "";
        this.place = "";
        this.isImportant = false;
        this.contacts = "";
    }
}
