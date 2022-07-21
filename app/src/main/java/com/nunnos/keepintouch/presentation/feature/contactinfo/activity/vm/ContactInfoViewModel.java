package com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm;

import android.content.Context;

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

    private final MediatorLiveData<Contact> contactMD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Conversation>> conversationsMD = new MediatorLiveData<>();

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
            contactMD.postValue(Contact.map(contactEntity));
        });
    }

    private void setConversationDao(Context context) {
        if (conversationDao != null) return;
        conversationDao = ConversationDB.getInstance(context).conversationDao();
    }

    public void retrieveConversations(Context context) {
        setConversationDao(context);
        if (contactMD != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                List<ConversationEntity> conversationEntityList = conversationDao.getAllFromContactId(SEPARATOR + contactMD.getValue().getId() + SEPARATOR);
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

    public void addNewConversation(Context context, Conversation newConversation) {
        setConversationDao(context);
        if (contactMD != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                conversationDao.insert(ConversationEntity.map(newConversation));
            });
        } else {
            //TODO SHOW ERROR
        }
    }

    public MediatorLiveData<Contact> getContact() {
        return contactMD;
    }

    public MediatorLiveData<List<Conversation>> getConversations() {
        return conversationsMD;
    }

}
