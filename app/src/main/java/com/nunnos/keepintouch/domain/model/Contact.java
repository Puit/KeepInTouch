package com.nunnos.keepintouch.domain.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.data.entities.contactdao.ContactEntity;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;

public class Contact {
    private int id;
    private String photo;
    private String name;
    private String surname1;
    private String surname2;
    private String gender;
    private String sexualOrientation;
    private String birthday;
    private boolean realBirthday;
    private String address;
    private String profession;
    private String placeOfWork;
    private String howWeMet;
    private String language;
    private String religion;
    private String relatives; //Seran los Ids de los familiares
    private String conversations;//Seran los Ids de las conversaciones
    private boolean favorite;//Seran los Ids de las conversaciones
    private int bgColor;

    public Contact(int id, String name, String surname1, String surname2, String gender, String sexualOrientation, String birthday,
                   boolean realBirthday, String address, String profession, String placeOfWork, String howWeMet,
                   String language, String religion, String relatives, String conversations,
                   boolean favorite, int bgColor, String photo) {
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
    }

    public Contact(String name, String surname1, String surname2, String gender, String sexualOrientation, String birthday,
                   boolean realBirthday, String address, String profession, String placeOfWork, String howWeMet,
                   String language, String religion, String relatives, String conversations,
                   boolean favorite, int bgColor, String photo) {
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
    }

    public Contact() {
        //Required
    }

    private Contact(ContactEntity entity) {
        setId(entity.id);
        setName(entity.name);
        setSurname1(entity.surname1);
        setSurname2(entity.surname2);
        setGender(entity.gender);
        setSexualOrientation(entity.sexualOrientation);
        setBirthday(entity.birthday);
        setRealBirthday(entity.realBirthday);
        setAddress(entity.address);
        setProfession(entity.profession);
        setPlaceOfWork(entity.placeOfWork);
        setHowWeMet(entity.howWeMet);
        setLanguage(entity.language);
        setReligion(entity.religion);
        setRelatives(entity.relatives);
        setConversations(entity.conversations);
        setPhoto(entity.photo);
    }

    public static Contact map(ContactEntity entity) {
        return new Contact(entity.id, entity.name, entity.surname1, entity.surname2, entity.gender,
                entity.sexualOrientation, entity.birthday, entity.realBirthday, entity.address, entity.profession,
                entity.placeOfWork, entity.howWeMet, entity.language, entity.religion, entity.relatives,
                entity.conversations, entity.favorite, entity.bgColor, entity.photo);
    }

    public int getAge() {
        CustomDate customDate = CustomDate.dateFromString(birthday);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, customDate.getDay());
        calendar.set(Calendar.MONTH, customDate.getMonth());
        calendar.set(Calendar.YEAR, customDate.getYear());
        LocalDate birthdayLocalDate = Instant.ofEpochMilli(calendar.getTimeInMillis()).atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(LocalDate.from(birthdayLocalDate), LocalDate.now());
        return period.getYears();
    }

    public String getFullName() {
        String fullName = name;
        if (!TextUtils.isEmpty(surname1)) {
            fullName = fullName + " " + surname1;
        }
        if (!TextUtils.isEmpty(surname2)) {
            fullName = fullName + " " + surname2;
        }
        return fullName;
    }

    public String getDaysSinceLastChat() {
        return "";
    }

    public boolean isEmpty() {
        boolean empty = true;
        if (id > 0) {
            empty = false;
        }
        if (photo == null) {
            empty = false;
        }
        if (name == null) {
            empty = false;
        }
        if (surname1 == null) {
            empty = false;
        }
        if (surname2 == null) {
            empty = false;
        }
        if (gender == null) {
            empty = false;
        }
        if (sexualOrientation == null) {
            empty = false;
        }
        if (birthday == null) {
            empty = false;
        }
        if (address == null) {
            empty = false;
        }
        if (profession == null) {
            empty = false;
        }
        if (placeOfWork == null) {
            empty = false;
        }
        if (howWeMet == null) {
            empty = false;
        }
        if (language == null) {
            empty = false;
        }
        if (religion == null) {
            empty = false;
        }
        if (relatives == null) {
            empty = false;
        }
        if (conversations == null) {
            empty = false;
        }
        return empty;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSexualOrientation() {
        return sexualOrientation;
    }

    public void setSexualOrientation(String sexualOrientation) {
        this.sexualOrientation = sexualOrientation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setRealBirthday(boolean realBirthday) {
        this.realBirthday = realBirthday;
    }

    public boolean isRealBirthday() {
        return realBirthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public String getHowWeMet() {
        return howWeMet;
    }

    public void setHowWeMet(String howWeMet) {
        this.howWeMet = howWeMet;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRelatives() {
        return relatives;
    }

    public void setRelatives(String relatives) {
        this.relatives = relatives;
    }

    public String getConversations() {
        return conversations;
    }

    public void setConversations(String conversations) {
        this.conversations = conversations;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public Bitmap getBitmapPhoto() {
        //TODO: ASK FOR PERMISSIONS
        if (photo != null) {
            File image = new File(photo);
            return BitmapFactory.decodeFile(image.getAbsolutePath());
        }
        return null;
    }
}
