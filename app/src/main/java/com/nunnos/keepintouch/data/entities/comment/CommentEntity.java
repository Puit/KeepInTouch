package com.nunnos.keepintouch.data.entities.comment;


import static com.nunnos.keepintouch.data.entities.comment.CommentDaoConstants.TABLE_NAME;
import static com.nunnos.keepintouch.domain.model.Conversation.SEPARATOR;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nunnos.keepintouch.domain.model.Comment;

@Entity(tableName = TABLE_NAME)
public class CommentEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "info")
    public String info;

    @ColumnInfo(name = "isImportant")
    public boolean isImportant;

    @ColumnInfo(name = "whoTold")
    public String whoTold;

    public CommentEntity(int id, String date, String info, boolean isImportant, String whoTold) {
        this.id = id;
        this.date = date;
        this.info = info;
        this.isImportant = isImportant;
        addContacts(whoTold);
    }

    public CommentEntity(String date, String info, boolean isImportant, String whoTold) {
        this.id = id;
        this.date = date;
        this.info = info;
        this.isImportant = isImportant;
        addContacts(whoTold);
    }

    public CommentEntity() {
        this.id = -2;
        this.date = "";
        this.info = "";
        this.isImportant = false;
        this.whoTold = SEPARATOR;
    }

    public static CommentEntity map(Comment c) {
        if (c.getId() != 0) {
            return new CommentEntity(c.getId(), c.getDate(), c.getInfo(), c.isImportant(), c.getWhoTold());
        } else {
            return new CommentEntity(c.getDate(), c.getInfo(), c.isImportant(), c.getWhoTold());
        }
    }

    private void addContacts(String contacts) {
        if (contacts.startsWith(SEPARATOR)) {
            this.whoTold = contacts;
        } else {
            this.whoTold = SEPARATOR + contacts;
        }
    }
}
