package com.nunnos.keepintouch.data.entities.contactdao;

import static com.nunnos.keepintouch.data.entities.contactdao.ContactsDaoConstants.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nunnos.keepintouch.domain.model.Contact;

@Entity(tableName = TABLE_NAME)
public class ContactEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "photo")
    public String photo;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "surname1")
    public String surname1;

    @ColumnInfo(name = "surname2")
    public String surname2;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "sexualorientation")
    public String sexualOrientation;

    @ColumnInfo(name = "birthday")
    public String birthday;

    @ColumnInfo(name = "realbirthday")
    public boolean realBirthday;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "profession")
    public String profession;

    @ColumnInfo(name = "placeofwork")
    public String placeOfWork;

    @ColumnInfo(name = "howwemet")
    public String howWeMet;

    @ColumnInfo(name = "language")
    public String language;

    @ColumnInfo(name = "religion")
    public String religion;

    @ColumnInfo(name = "relatives")
    public String relatives; //Seran los Ids de los familiares

    @ColumnInfo(name = "conversations")
    public String conversations; //Seran los Ids de las conversaciones

    @ColumnInfo(name = "favorite")
    public boolean favorite;

    @ColumnInfo(name = "bgcolor")
    public int bgColor;

    @ColumnInfo(name = "angle")
    public float angle;

    @ColumnInfo(name = "lastactionindex")
    public int lastActionIndex;

    @ColumnInfo(name = "alias")
    public String alias;

    @ColumnInfo(name = "telephone")
    public String telephone;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "comments")
    public String comments;

    @ColumnInfo(name = "daystocall")
    public int daysToCall;

    @ColumnInfo(name = "dayofdeath")
    public String dayOfDeath;

    @ColumnInfo(name = "socialmedia")
    public String socialMedia;

    @ColumnInfo(name = "notification")
    public String notification;

    public ContactEntity(int id, String name, String surname1, String surname2, String gender, String sexualOrientation, String birthday,
                         boolean realBirthday, String address, String profession, String placeOfWork, String howWeMet,
                         String language, String religion, String relatives, String conversations,
                         boolean favorite, int bgColor, String photo, float angle, int lastActionIndex, String alias,
                         String telephone, String email, String comments, int daysToCall, String dayOfDeath,
                         String socialMedia, String notification) {
        this.id = id;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.gender = gender;
        this.sexualOrientation = sexualOrientation;
        this.birthday = birthday;
        this.realBirthday = realBirthday;
        this.address = address;
        this.profession = profession;
        this.placeOfWork = placeOfWork;
        this.howWeMet = howWeMet;
        this.language = language;
        this.religion = religion;
        this.relatives = relatives;
        this.conversations = conversations;
        this.favorite = favorite;
        this.bgColor = bgColor;
        this.photo = photo;
        this.angle = angle;
        this.lastActionIndex = lastActionIndex;
        this.alias = alias;
        this.telephone = telephone;
        this.email = email;
        this.comments = comments;
        this.daysToCall = daysToCall;
        this.dayOfDeath = dayOfDeath;
        this.socialMedia = socialMedia;
        this.notification = notification;
    }

    public ContactEntity(String name, String surname1, String surname2, String gender, String sexualOrientation, String birthday,
                         boolean realBirthday, String address, String profession, String placeOfWork, String howWeMet,
                         String language, String religion, String relatives, String conversations,
                         boolean favorite, int bgColor, String photo, float angle, int lastActionIndex, String alias,
                         String telephone, String email, String comments, int daysToCall, String dayOfDeath,
                         String socialMedia, String notification) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.gender = gender;
        this.sexualOrientation = sexualOrientation;
        this.realBirthday = realBirthday;
        this.birthday = birthday;
        this.address = address;
        this.profession = profession;
        this.placeOfWork = placeOfWork;
        this.howWeMet = howWeMet;
        this.language = language;
        this.religion = religion;
        this.relatives = relatives;
        this.conversations = conversations;
        this.favorite = favorite;
        this.bgColor = bgColor;
        this.photo = photo;
        this.angle = angle;
        this.lastActionIndex = lastActionIndex;
        this.alias = alias;
        this.telephone = telephone;
        this.email = email;
        this.comments = comments;
        this.daysToCall = daysToCall;
        this.dayOfDeath = dayOfDeath;
        this.socialMedia = socialMedia;
        this.notification = notification;
    }

    public ContactEntity() {

    }

    public static ContactEntity map(Contact c) {
        if (c.getId() != 0) {
            return new ContactEntity(c.getId(), c.getName(), c.getSurname1(), c.getSurname2(), c.getGender(),
                    c.getSexualOrientation(), c.getBirthday(), c.isRealBirthday(), c.getAddress(), c.getProfession(),
                    c.getPlaceOfWork(), c.getHowWeMet(), c.getLanguage(), c.getReligion(), c.getRelatives(),
                    c.getConversations(), c.isFavorite(), c.getBgColor(), c.getPhoto(), c.getAngle(),
                    c.getLastActionIndex(), c.getAlias(), c.getTelephone(), c.getEmail(), c.getComments(),
                    c.getDaysToCall(), c.getDayOfDeath(), c.getSocialMedia(), c.getNotification());
        } else {
            return new ContactEntity(c.getName(), c.getSurname1(), c.getSurname2(), c.getGender(),
                    c.getSexualOrientation(), c.getBirthday(), c.isRealBirthday(), c.getAddress(), c.getProfession(),
                    c.getPlaceOfWork(), c.getHowWeMet(), c.getLanguage(), c.getReligion(), c.getRelatives(),
                    c.getConversations(), c.isFavorite(), c.getBgColor(), c.getPhoto(), c.getAngle(),
                    c.getLastActionIndex(), c.getAlias(), c.getTelephone(), c.getEmail(), c.getComments(),
                    c.getDaysToCall(), c.getDayOfDeath(), c.getSocialMedia(), c.getNotification());
        }
    }
}
