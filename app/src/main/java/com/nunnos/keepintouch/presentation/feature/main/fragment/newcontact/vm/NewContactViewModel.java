package com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.vm;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;

import com.nunnos.keepintouch.domain.model.Contact;

public class NewContactViewModel extends ViewModel implements LifecycleObserver {
    private Contact contact;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }


    public int getContactId() {
        return contact.getId();
    }

    public void setContactId(int id) {
        this.contact.setId(id);
    }

    public String getContactPhoto() {
        return contact.getPhoto();
    }

    public void setContactPhoto(String photo) {
        this.contact.setPhoto(photo);
    }

    public String getContactName() {
        return contact.getName();
    }

    public void setContactName(String name) {
        this.contact.setName(name);
    }

    public String getContactSurname1() {
        return contact.getSurname1();
    }

    public void setContactSurname1(String surname1) {
        this.contact.setSurname1(surname1);
    }

    public String getContactSurname2() {
        return contact.getSurname2();
    }

    public void setContactSurname2(String surname2) {
        this.contact.setSurname2(surname2);
    }

    public String getContactGender() {
        return contact.getGender();
    }

    public void setContactGender(String gender) {
        this.contact.setGender(gender);
    }

    public String getContactSexualOrientation() {
        return contact.getSexualOrientation();
    }

    public void setContactSexualOrientation(String sexualOrientation) {
        this.contact.setSexualOrientation(sexualOrientation);
    }

    public String getContactBirthday() {
        return contact.getBirthday();
    }

    public void setContactBirthday(String birthday) {
        this.contact.setBirthday(birthday);
    }

    public String getContactAddress() {
        return contact.getAddress();
    }

    public void setContactAddress(String address) {
        this.contact.setAddress(address);
    }

    public String getContactProfession() {
        return contact.getProfession();
    }

    public void setContactProfession(String profession) {
        this.contact.setProfession(profession);
    }

    public String getContactPlaceOfWork() {
        return contact.getPlaceOfWork();
    }

    public void setContactPlaceOfWork(String placeOfWork) {
        this.contact.setPlaceOfWork(placeOfWork);
    }

    public String getContactHowWeMet() {
        return contact.getHowWeMet();
    }

    public void setContactHowWeMet(String howWeMet) {
        this.contact.setHowWeMet(howWeMet);
    }

    public String getContactLanguage() {
        return contact.getLanguage();
    }

    public void setContactLanguage(String language) {
        this.contact.setLanguage(language);
    }

    public String getContactReligion() {
        return contact.getReligion();
    }

    public void setContactReligion(String religion) {
        this.contact.setReligion(religion);
    }

    public String getContactRelatives() {
        return contact.getRelatives();
    }

    public void setContactRelatives(String relatives) {
        this.contact.setRelatives(relatives);
    }

    public String getContactConversations() {
        return contact.getConversations();
    }

    public void setContactConversations(String conversations) {
        this.contact.setConversations(conversations);
    }

    public void setContactFavorite(boolean favorite) {
        this.contact.setFavorite(false);
    }

    public boolean isFavorite() {
        return contact.isFavorite();
    }

    public void setContactBgColor(int bgColor) {
        this.contact.setBgColor(bgColor);
    }

    public int getContactBgColor() {
        return contact.getBgColor();
    }
}
