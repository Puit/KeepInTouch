package com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MediatorLiveData;

import com.nunnos.keepintouch.data.AppExecutors;
import com.nunnos.keepintouch.data.entities.contactdao.ContactDB;
import com.nunnos.keepintouch.data.entities.contactdao.ContactDao;
import com.nunnos.keepintouch.data.entities.contactdao.ContactEntity;
import com.nunnos.keepintouch.data.entities.conversation.ConversationDB;
import com.nunnos.keepintouch.data.entities.conversation.ConversationDao;
import com.nunnos.keepintouch.data.entities.conversation.ConversationEntity;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.domain.model.Conversation;

import java.util.ArrayList;
import java.util.List;

import static com.nunnos.keepintouch.domain.model.Conversation.SEPARATOR;

public class ContactInfoViewModel extends ContactInfoNavigationViewModel implements LifecycleObserver {

    private ContactDao contactDao;
    private ConversationDao conversationDao;
    private Conversation newConversation = new Conversation();

    private final MediatorLiveData<Contact> thisContactMD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsMD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Conversation>> conversationsMD = new MediatorLiveData<>();
    private final MediatorLiveData<Bitmap> newConversationBitmap = new MediatorLiveData<>();

    private void setContactDao(Context context) {
        if (contactDao != null) return;
        contactDao = ContactDB.getInstance(context).contactDao();
    }

    public void retrieveContact(Context context, int id) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            ContactEntity contactEntity = contactDao.get(id);
            if (contactEntity == null) {
                //TODO: SHOW ERROR?
                return;
            }
            thisContactMD.postValue(Contact.map(contactEntity));
        });
    }

    private void setConversationDao(Context context) {
        if (conversationDao != null) return;
        conversationDao = ConversationDB.getInstance(context).conversationDao();
    }

    public void retrieveConversations(Context context) {
        setConversationDao(context);
        if (thisContactMD != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                List<ConversationEntity> conversationEntityList = conversationDao.getAllFromContactId(SEPARATOR + thisContactMD.getValue().getId() + SEPARATOR);
                if (conversationEntityList.isEmpty()) {
                    //TODO: SHOW ERROR?
                    return;
                }
                ArrayList<Conversation> conversations = new ArrayList<>();
                for (ConversationEntity entity : conversationEntityList) {
                    conversations.add(Conversation.map(entity));
                }
                conversationsMD.postValue(conversations);
            });
        } else {
            //TODO SHOW ERROR
        }
    }

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
            contactsMD.postValue(contacts);
        });
    }

    public void addNewConversation(Context context, Conversation newConversation) {
        setConversationDao(context);
        if (thisContactMD != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                conversationDao.insert(ConversationEntity.map(newConversation));
            });
        } else {
            //TODO SHOW ERROR
        }
    }

    public MediatorLiveData<Bitmap> getNewConversationBitmap() {
        return newConversationBitmap;
    }

    public void setNewConversationBitmap(Bitmap newConversationBitmap) {
        this.newConversationBitmap.setValue(newConversationBitmap);
    }

    public MediatorLiveData<Contact> getThisContact() {
        return thisContactMD;
    }

    public MediatorLiveData<List<Contact>> getContactsList() {
        return contactsMD;
    }

    public MediatorLiveData<List<Conversation>> getConversations() {
        return conversationsMD;
    }

    public Conversation getNewConversation() {
        return newConversation;
    }

    public void setNewConversation(Conversation newConversation) {
        this.newConversation = newConversation;
    }
}
