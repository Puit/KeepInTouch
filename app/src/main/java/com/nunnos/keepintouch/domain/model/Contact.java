package com.nunnos.keepintouch.domain.model;

import static com.nunnos.keepintouch.utils.Constants.CONTACTS_SEPARATOR;

import android.content.Context;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.data.entities.contactdao.ContactEntity;
import com.nunnos.keepintouch.utils.TextUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

public class Contact {
    private int id;
    private String photo = "";
    private String name = "";
    private String surname1 = "";
    private String surname2 = "";
    private String gender = "";
    private String sexualOrientation = "";
    private String birthday = "";
    private boolean realBirthday;
    private String address = "";
    private String profession = "";
    private String placeOfWork = "";
    private String howWeMet = "";
    private String language = "";
    private String religion = "";
    private String relatives = ""; //Seran los Ids de los familiares
    private String conversations = "";//Seran los Ids de las conversaciones
    private boolean favorite;//Seran los Ids de las conversaciones
    private int bgColor;
    private float angle;
    private int lastActionIndex;
    private String alias = "";
    private String telephone = "";
    private String email = "";
    private String comments = "";
    private int daysToCall;
    private String dayOfDeath = "";
    private String socialMedia = "";
    private String notification = "";

    public Contact(int id, String name, String surname1, String surname2, String gender, String sexualOrientation, String birthday,
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

    public Contact(String name, String surname1, String surname2, String gender, String sexualOrientation, String birthday,
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

    public Contact() {
        //Required
    }

    public static Contact map(ContactEntity entity) {
        return new Contact(entity.id, entity.name, entity.surname1, entity.surname2, entity.gender,
                entity.sexualOrientation, entity.birthday, entity.realBirthday, entity.address, entity.profession,
                entity.placeOfWork, entity.howWeMet, entity.language, entity.religion, entity.relatives,
                entity.conversations, entity.favorite, entity.bgColor, entity.photo, entity.angle,
                entity.lastActionIndex, entity.alias, entity.telephone, entity.email, entity.comments,
                entity.daysToCall, entity.dayOfDeath, entity.socialMedia, entity.notification);
    }

    public int getAge() {
        //TODO: Comprobar si ha muerto
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CustomDate customDate = CustomDate.dateFromString(birthday);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, customDate.getDay());
            calendar.set(Calendar.MONTH, customDate.getMonth() - 1); //Los meses empiezan en 0
            calendar.set(Calendar.YEAR, customDate.getYear());
            LocalDate birthdayLocalDate = null;
            birthdayLocalDate = Instant.ofEpochMilli(calendar.getTimeInMillis()).atZone(ZoneId.systemDefault()).toLocalDate();
            Period period = Period.between(LocalDate.from(birthdayLocalDate), LocalDate.now());
            return period.getYears();
        }
        return -1;
    }

    public String getFullName() {
        String fullName = "";
        if (!TextUtils.isEmpty(name)) {
            fullName = fullName + name.trim();
        }
        if (!TextUtils.isEmpty(surname1)) {
            fullName = fullName + " " + surname1.trim();
        }
        if (!TextUtils.isEmpty(surname2)) {
            fullName = fullName + " " + surname2.trim();
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
        if (TextUtils.isEmpty(photo)) {
            empty = false;
        }
        if (TextUtils.isEmpty(name)) {
            empty = false;
        }
        if (TextUtils.isEmpty(surname1)) {
            empty = false;
        }
        if (TextUtils.isEmpty(surname2)) {
            empty = false;
        }
        if (TextUtils.isEmpty(gender)) {
            empty = false;
        }
        if (TextUtils.isEmpty(sexualOrientation)) {
            empty = false;
        }
        if (TextUtils.isEmpty(birthday)) {
            empty = false;
        }
        if (TextUtils.isEmpty(address)) {
            empty = false;
        }
        if (TextUtils.isEmpty(profession)) {
            empty = false;
        }
        if (TextUtils.isEmpty(placeOfWork)) {
            empty = false;
        }
        if (TextUtils.isEmpty(howWeMet)) {
            empty = false;
        }
        if (TextUtils.isEmpty(language)) {
            empty = false;
        }
        if (TextUtils.isEmpty(religion)) {
            empty = false;
        }
        if (TextUtils.isEmpty(relatives)) {
            empty = false;
        }
        if (TextUtils.isEmpty(conversations)) {
            empty = false;
        }
        if (TextUtils.isEmpty(telephone)) {
            empty = false;
        }
        if (TextUtils.isEmpty(email)) {
            empty = false;
        }
        if (TextUtils.isEmpty(dayOfDeath)) {
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

    public void addRelative(int contactId) {
        if (containsRelative(contactId)) return;
        relatives = relatives + contactId + CONTACTS_SEPARATOR;
    }

    public void addRelativeList(List<Contact> contactList) {
        relatives = CONTACTS_SEPARATOR;
        for (Contact c : contactList) {
            addRelative(c.getId());
        }
    }

    public boolean containsRelative(int contactId) {
        return relatives.contains(CONTACTS_SEPARATOR + contactId + CONTACTS_SEPARATOR);
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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getLastActionIndex() {
        return lastActionIndex;
    }

    public void setLastActionIndex(int lastActionIndex) {
        this.lastActionIndex = lastActionIndex;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getDaysToCall() {
        return daysToCall;
    }

    public void setDaysToCall(int daysToCall) {
        this.daysToCall = daysToCall;
    }

    public String getDayOfDeath() {
        return dayOfDeath;
    }

    public void setDayOfDeath(String dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public static boolean areSameContact(Contact c1, Contact c2) {
        boolean isTheSame = true;

        if (c1.getId() != c2.getId())
            isTheSame = false;
        if (!isStringTheSame(c1.getPhoto(), c2.getPhoto()))
            isTheSame = false;
        if (!isStringTheSame(c1.getName(), c2.getName()))
            isTheSame = false;
        if (!isStringTheSame(c1.getSurname1(), c2.getSurname1()))
            isTheSame = false;
        if (!isStringTheSame(c1.getSurname2(), c2.getSurname2()))
            isTheSame = false;
        if (!isStringTheSame(c1.getGender(), c2.getGender()))
            isTheSame = false;
        if (!isStringTheSame(c1.getSexualOrientation(), c2.getSexualOrientation()))
            isTheSame = false;
        if (!isStringTheSame(c1.getBirthday(), c2.getBirthday()))
            isTheSame = false;
        if (c1.isRealBirthday() != c2.isRealBirthday())
            isTheSame = false;
        if (!isStringTheSame(c1.getAddress(), c2.getAddress()))
            isTheSame = false;
        if (!isStringTheSame(c1.getProfession(), c2.getProfession()))
            isTheSame = false;
        if (!isStringTheSame(c1.getPlaceOfWork(), c2.getPlaceOfWork()))
            isTheSame = false;
        if (!isStringTheSame(c1.getHowWeMet(), c2.getHowWeMet()))
            isTheSame = false;
        if (!isStringTheSame(c1.getLanguage(), c2.getLanguage()))
            isTheSame = false;
        if (!isStringTheSame(c1.getReligion(), c2.getReligion()))
            isTheSame = false;
        if (!isStringTheSame(c1.getRelatives(), c2.getRelatives()))
            isTheSame = false;
        if (!isStringTheSame(c1.getConversations(), c2.getConversations()))
            isTheSame = false;
        if (c1.isFavorite() != c2.isFavorite())
            isTheSame = false;
        if (c1.getBgColor() != c2.getBgColor())
            isTheSame = false;
        if (c1.getAngle() != c2.getAngle())
            isTheSame = false;
        if (c1.getLastActionIndex() != c2.getLastActionIndex())
            isTheSame = false;
        if (!isStringTheSame(c1.getAlias(), c2.getAlias()))
            isTheSame = false;
        if (!isStringTheSame(c1.getTelephone(), c2.getTelephone()))
            isTheSame = false;
        if (!isStringTheSame(c1.getEmail(), c2.getEmail()))
            isTheSame = false;
        if (!isStringTheSame(c1.getComments(), c2.getComments()))
            isTheSame = false;
        if (c1.getDaysToCall() != c2.getDaysToCall())
            isTheSame = false;
        if (!isStringTheSame(c1.getDayOfDeath(), c2.getDayOfDeath()))
            isTheSame = false;

        return isTheSame;
    }

    private static boolean isStringTheSame(String s1, String s2) {
        if (TextUtils.isEmpty(s1) && TextUtils.isEmpty(s2))
            return true;
        if (TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2) || !TextUtils.isEmpty(s1) && TextUtils.isEmpty(s2))
            return false;
        return s1.equals(s2);
    }

    public static String createFakeBirthdayFromAge(String age, Context context) {
        if (TextUtils.isNumeric(age)) {
            return createFakeBirthdayFromAge(Integer.parseInt(age), context);
        }
        return age;
    }

    public static String createFakeBirthdayFromAge(int age, Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, 0);//Enero es el 0
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - age);
        calendar.get(Calendar.YEAR); //Requerido para que setee el valor
        return TextUtils.dateToString(calendar, context.getString(R.string.date_format));
    }
}
