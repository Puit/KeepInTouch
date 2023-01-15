package com.nunnos.keepintouch.data.entities.comment;


import static com.nunnos.keepintouch.data.entities.comment.CommentDaoConstants.TABLE_NAME;
import static com.nunnos.keepintouch.utils.Constants.CONTACTS_SEPARATOR;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nunnos.keepintouch.domain.model.complements.Comment;


@Entity(tableName = TABLE_NAME)
public class CommentEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "contactId")
    public int contactId;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "info")
    public String info;

    @ColumnInfo(name = "isImportant")
    public boolean isImportant;

    @ColumnInfo(name = "whoTold")
    public String whoTold;

    public CommentEntity(int id, int contactId, String date, String info, boolean isImportant, String whoTold) {
        this.id = id;
        this.contactId = contactId;
        this.date = date;
        this.info = info;
        this.isImportant = isImportant;
        addContacts(whoTold);
    }

    public CommentEntity(int contactId, String date, String info, boolean isImportant, String whoTold) {
        this.contactId = contactId;
        this.date = date;
        this.info = info;
        this.isImportant = isImportant;
        addContacts(whoTold);
    }

    public CommentEntity() {
        this.id = -2;
        this.contactId = -2;
        this.date = "";
        this.info = "";
        this.isImportant = false;
        this.whoTold = CONTACTS_SEPARATOR;
    }

    public static CommentEntity map(Comment c) {
        if (c.getId() != 0) {
            return new CommentEntity(c.getId(), c.getContactId(),c.getDate(), c.getInfo(), c.isImportant(), c.getWhoTold());
        } else {
            return new CommentEntity(c.getContactId(),c.getDate(), c.getInfo(), c.isImportant(), c.getWhoTold());
        }
    }

    private void addContacts(String contacts) {
        if (TextUtils.isEmpty(contacts)) return;
        if (contacts.startsWith(CONTACTS_SEPARATOR)) {
            this.whoTold = contacts;
        } else {
            this.whoTold = CONTACTS_SEPARATOR + contacts;
        }
    }
}
