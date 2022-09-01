package com.nunnos.keepintouch.presentation.feature.main.activity.vm;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MediatorLiveData;

import com.nunnos.keepintouch.data.AppExecutors;
import com.nunnos.keepintouch.data.entities.contactdao.ContactDB;
import com.nunnos.keepintouch.data.entities.contactdao.ContactDao;
import com.nunnos.keepintouch.data.entities.contactdao.ContactEntity;
import com.nunnos.keepintouch.domain.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends MainNavigationViewModel implements LifecycleObserver {

    private ContactDao contactDao;
    private Contact newContact = new Contact();
    private int contactSelectedID = -1;
    private final MediatorLiveData<Bitmap> newContactBitmap = new MediatorLiveData<>();

    private final MediatorLiveData<List<Contact>> contactsMutableLiveData = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByNameLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByGenderLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundBySexualOrientationLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByBirthdayLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByAddressLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByProfessionLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByPlaceOfWorkLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByHowWeMetLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByLanguageLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByReligionLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByRelativesLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByConversationsLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByAliasLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByTelephoneLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByEmailLD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsFoundByCommentsLD = new MediatorLiveData<>();

    private int lastIndex = 0;

    public void retrieveContacts(Context context) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (contactDao.getAllOrderByLastIndexDes().isEmpty()) {
                //TODO: SHOW ERROR?
                return;
            }
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.getAllOrderByLastIndexDes()) {
                contacts.add(Contact.map(entity));
            }
            contactsMutableLiveData.postValue(contacts);
        });
    }

    public void saveContact(Context context, Contact contact) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            contactDao.insert(ContactEntity.map(contact));
        });
    }

    public MediatorLiveData<List<Contact>> getContacts() {
        return contactsMutableLiveData;
    }

    private void setContactDao(Context context) {
        if (contactDao != null) return;
        contactDao = ContactDB.getInstance(context).contactDao();
    }

    public Contact getNewContact() {
        return newContact;
    }

    public void setNewContact(Contact newContact) {
        this.newContact = newContact;
    }

    public MediatorLiveData<Bitmap> getNewContactBitmap() {
        return newContactBitmap;
    }

    public void setNewContactBitmap(Bitmap newContactBitmap) {
        this.newContactBitmap.setValue(newContactBitmap);
    }

    public int getContactSelectedID() {
        return contactSelectedID;
    }

    public void setContactSelectedID(int contactSelectedID) {
        this.contactSelectedID = contactSelectedID;
    }

    public void retrieveLastIndex(Context context) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            lastIndex = contactDao.getLastIndex();
        });
    }

    public void searchByName(Context context, String name) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByName(name)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByNameLD.postValue(contacts);
        });
    }

    public void searchByGender(Context context, String gender) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByGender(gender)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByGenderLD.postValue(contacts);
        });
    }

    public void searchBySexualOrientation(Context context, String sexualOrientation) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchBySexualOrientation(sexualOrientation)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundBySexualOrientationLD.postValue(contacts);
        });
    }

    public void searchByBirthday(Context context, String birthday) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByBirthday(birthday)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByBirthdayLD.postValue(contacts);
        });
    }

    public void searchByAddress(Context context, String address) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByAddress(address)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByAddressLD.postValue(contacts);
        });
    }

    public void searchByProfession(Context context, String profession) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByProfession(profession)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByProfessionLD.postValue(contacts);
        });
    }

    public void searchByPlaceOfWork(Context context, String placeOfWork) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByPlaceOfWork(placeOfWork)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByPlaceOfWorkLD.postValue(contacts);
        });
    }

    public void searchByHowWeMet(Context context, String howWeMet) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByHowWeMet(howWeMet)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByHowWeMetLD.postValue(contacts);
        });
    }

    public void searchByLanguage(Context context, String language) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByLanguage(language)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByLanguageLD.postValue(contacts);
        });
    }

    public void searchByReligion(Context context, String religion) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByReligion(religion)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByReligionLD.postValue(contacts);
        });
    }

    public void searchByRelatives(Context context, String relatives) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByRelatives(relatives)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByRelativesLD.postValue(contacts);
        });
    }

    public void searchByConversations(Context context, String conversation) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByConversations(conversation)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByConversationsLD.postValue(contacts);
        });
    }

    public void searchByAlias(Context context, String alias) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByAlias(alias)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByAliasLD.postValue(contacts);
        });
    }

    public void searchByTelephone(Context context, String telephone) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByTelephone(telephone)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByTelephoneLD.postValue(contacts);
        });
    }

    public void searchByEmail(Context context, String email) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByEmail(email)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByEmailLD.postValue(contacts);
        });
    }

    public void searchByComments(Context context, String comments) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.searchByComments(comments)) {
                contacts.add(Contact.map(entity));
            }
            contactsFoundByCommentsLD.postValue(contacts);
        });
    }
    public void clearSearch(){
        contactsMutableLiveData.postValue(null);
        contactsFoundByNameLD.postValue(null);
        contactsFoundByGenderLD.postValue(null);
        contactsFoundBySexualOrientationLD.postValue(null);
        contactsFoundByBirthdayLD.postValue(null);
        contactsFoundByAddressLD.postValue(null);
        contactsFoundByProfessionLD.postValue(null);
        contactsFoundByPlaceOfWorkLD.postValue(null);
        contactsFoundByHowWeMetLD.postValue(null);
        contactsFoundByLanguageLD.postValue(null);
        contactsFoundByReligionLD.postValue(null);
        contactsFoundByRelativesLD.postValue(null);
        contactsFoundByConversationsLD.postValue(null);
        contactsFoundByAliasLD.postValue(null);
        contactsFoundByTelephoneLD.postValue(null);
        contactsFoundByEmailLD.postValue(null);
        contactsFoundByCommentsLD.postValue(null);
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByNameLD() {
        return contactsFoundByNameLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByGenderLD() {
        return contactsFoundByGenderLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundBySexualOrientationLD() {
        return contactsFoundBySexualOrientationLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByBirthdayLD() {
        return contactsFoundByBirthdayLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByAddressLD() {
        return contactsFoundByAddressLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByProfessionLD() {
        return contactsFoundByProfessionLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByPlaceOfWorkLD() {
        return contactsFoundByPlaceOfWorkLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByHowWeMetLD() {
        return contactsFoundByHowWeMetLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByLanguageLD() {
        return contactsFoundByLanguageLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByReligionLD() {
        return contactsFoundByReligionLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByRelativesLD() {
        return contactsFoundByRelativesLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByConversationsLD() {
        return contactsFoundByConversationsLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByAliasLD() {
        return contactsFoundByAliasLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByTelephoneLD() {
        return contactsFoundByTelephoneLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByEmailLD() {
        return contactsFoundByEmailLD;
    }

    public MediatorLiveData<List<Contact>> getContactsFoundByCommentsLD() {
        return contactsFoundByCommentsLD;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }
}
