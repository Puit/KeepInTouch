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
    private final MediatorLiveData<Bitmap> newContactBitmap = new MediatorLiveData<>();

    private final MediatorLiveData<List<Contact>> contactsMutableLiveData = new MediatorLiveData<>();

    public void retrieveContacts(Context context) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (contactDao.getAll().isEmpty()) {
                //TODO: SHOW ERROR?
                return;
            }
            ArrayList<Contact> contacts = new ArrayList<>();
            for (ContactEntity entity : contactDao.getAll()) {
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

}
