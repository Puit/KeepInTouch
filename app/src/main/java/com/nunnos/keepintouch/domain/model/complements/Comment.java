package com.nunnos.keepintouch.domain.model.complements;

import com.nunnos.keepintouch.data.entities.comment.CommentEntity;
import com.nunnos.keepintouch.domain.model.complements.base.Complement;

public class Comment extends Complement {

    private int id;
    private String date;
    private String info;
    private boolean isImportant;
    private String whoTold;

    public Comment() {
        //Required
    }

    public Comment(CommentEntity entity) {
        this.setId(entity.id);
        this.setDate(entity.date);
        this.setInfo(entity.info);
        this.setImportant(entity.isImportant);
        entity.whoTold = addContactsFirstTime(entity.whoTold);
    }

    public static Comment map(CommentEntity entity) {
        return new Comment(entity);
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getWhoTold() {
        return whoTold;
    }

    public void setWhoTold(String whoTold) {
        this.whoTold = whoTold;
    }

}
