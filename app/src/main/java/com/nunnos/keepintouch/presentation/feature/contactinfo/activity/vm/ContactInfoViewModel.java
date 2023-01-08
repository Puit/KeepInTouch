package com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm;

import static android.app.Activity.RESULT_OK;
import static com.nunnos.keepintouch.base.baseview.BaseActivityViewModelLiveData.RESULT;
import static com.nunnos.keepintouch.base.baseview.BaseActivityViewModelLiveData.WHAT_IM_CAPTURING;
import static com.nunnos.keepintouch.utils.Constants.CONTACTS_SEPARATOR;
import static com.nunnos.keepintouch.utils.Constants.KEY_DELETE_CONTACT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MediatorLiveData;

import com.nunnos.keepintouch.data.AppExecutors;
import com.nunnos.keepintouch.data.entities.comment.CommentDB;
import com.nunnos.keepintouch.data.entities.comment.CommentDao;
import com.nunnos.keepintouch.data.entities.comment.CommentEntity;
import com.nunnos.keepintouch.data.entities.contactdao.ContactDB;
import com.nunnos.keepintouch.data.entities.contactdao.ContactDao;
import com.nunnos.keepintouch.data.entities.contactdao.ContactEntity;
import com.nunnos.keepintouch.data.entities.conversation.ConversationDB;
import com.nunnos.keepintouch.data.entities.conversation.ConversationDao;
import com.nunnos.keepintouch.data.entities.conversation.ConversationEntity;
import com.nunnos.keepintouch.data.entities.notification.NotificationEntity;
import com.nunnos.keepintouch.data.entities.notification.NotificationsEntityManager;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.domain.model.complements.Comment;
import com.nunnos.keepintouch.domain.model.complements.Conversation;
import com.nunnos.keepintouch.domain.model.complements.base.Complement;
import com.nunnos.keepintouch.notifications.Notification;
import com.nunnos.keepintouch.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactInfoViewModel extends ContactInfoNavigationViewModel implements LifecycleObserver {

    private static final String TAG = "ContactInfoViewModel";
    private ContactDao contactDao;
    private ConversationDao conversationDao;
    private CommentDao commentDao;
    private Conversation newConversation = new Conversation();
    private Comment newComment = new Comment();
    private int lastIndex = 0;

    private final MediatorLiveData<Contact> thisContactMD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Contact>> contactsMD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Conversation>> conversationsMD = new MediatorLiveData<>();
    private final MediatorLiveData<List<Comment>> commentsMD = new MediatorLiveData<>();
    private final MediatorLiveData<Bitmap> newConversationBitmap = new MediatorLiveData<>();
    private final MediatorLiveData<Bitmap> thisContactBitmap = new MediatorLiveData<>();
    private final MediatorLiveData<NotificationEntity> thisContactNotificationMD = new MediatorLiveData<>();

    private ArrayList<Complement> complements = new ArrayList<>();

    private boolean updateOnBack = false;

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

    private void setCommentDao(Context context) {
        if (commentDao != null) return;
        commentDao = CommentDB.getInstance(context).commentDao();
    }

    public void retrieveConversations(Context context) {
        setConversationDao(context);
        if (thisContactMD != null && thisContactMD.getValue() != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                List<ConversationEntity> conversationEntityList = conversationDao.getAllFromContactId(CONTACTS_SEPARATOR + thisContactMD.getValue().getId() + CONTACTS_SEPARATOR);
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

    public void retrieveComments(Context context) {
        setCommentDao(context);
        if (thisContactMD != null && thisContactMD.getValue() != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                List<CommentEntity> commentEntityList = commentDao.getAllFromWhoToldId(CONTACTS_SEPARATOR + thisContactMD.getValue().getId() + CONTACTS_SEPARATOR);
                if (commentEntityList.isEmpty()) {
                    //TODO: SHOW ERROR?
                    return;
                }
                ArrayList<Comment> comments = new ArrayList<>();
                for (CommentEntity entity : commentEntityList) {
                    comments.add(Comment.map(entity));
                }
                commentsMD.postValue(comments);
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

    public void addNewComment(Context context, Comment newComment) {
        setCommentDao(context);
        if (thisContactMD != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                commentDao.insert(CommentEntity.map(newComment));
            });
        } else {
            //TODO SHOW ERROR
        }
    }

    public void deleteConversation(Context context, Conversation newConversation) {
        setConversationDao(context);
        if (thisContactMD != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                conversationDao.deleteById(newConversation.getId());
            });
        } else {
            //TODO SHOW ERROR
        }
    }

    public void deleteContact(Activity activity, Contact contact) {
        setContactDao(activity);
        if (thisContactMD != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                contactDao.deleteById(contact.getId());
                Intent replyIntent = new Intent();
                replyIntent.putExtra(RESULT, true);
                replyIntent.putExtra(WHAT_IM_CAPTURING, KEY_DELETE_CONTACT);
                activity.setResult(RESULT_OK, replyIntent);
                activity.finish();
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

    public MediatorLiveData<NotificationEntity> getThisContactNotification() {
        return thisContactNotificationMD;
    }

    public MediatorLiveData<List<Contact>> getContactsList() {
        return contactsMD;
    }

    public MediatorLiveData<List<Conversation>> getConversations() {
        return conversationsMD;
    }

    public MediatorLiveData<List<Comment>> getComments() {
        return commentsMD;
    }

    public Conversation getNewConversation() {
        return newConversation;
    }

    public void setNewConversation(Conversation newConversation) {
        this.newConversation = newConversation;
    }

    public void resetNewConversation() {
        newConversation = new Conversation();
    }

    public Comment getNewComment() {
        return newComment;
    }

    public void setNewComment(Comment newComment) {
        this.newComment = newComment;
    }

    public void updateThisContact(Context context) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (thisContactMD.getValue() == null) {
                Log.d(TAG, "updateThisContact: contact not found");
            }
            contactDao.update(ContactEntity.map(thisContactMD.getValue()));
        });
    }

    public void updateThisContact(Context context, Contact contact) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (thisContactMD.getValue() == null) {
                Log.d(TAG, "updateThisContact: contact not found");
            }
            contactDao.update(ContactEntity.map(contact));
        });
    }

    public void updateConversation(Context context) {
        setContactDao(context);
        ConversationEntity entity = ConversationEntity.map(newConversation);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (conversationsMD.getValue() == null) {
                Log.d(TAG, "updateConversation: conversations not found");
            }
            conversationDao.update(entity);
        });
    }

    public void updateComment(Context context) {
        setContactDao(context);
        CommentEntity entity = CommentEntity.map(newComment);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (commentsMD.getValue() == null) {
                Log.d(TAG, "updateComment: comment not found");
            }
            commentDao.update(entity);
        });
    }

    public void updateOnBack() {
        updateOnBack = true;
    }

    public boolean isUpdateOnBack() {
        return updateOnBack;
    }

    public MediatorLiveData<Bitmap> getThisContactBitmap() {
        return thisContactBitmap;
    }

    public void setThisContactBitmap(Bitmap newContactBitmap) {
        this.thisContactBitmap.setValue(newContactBitmap);
    }

    public void retrieveLastIndex(Context context) {
        setContactDao(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            lastIndex = contactDao.getLastIndex();
        });
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public ArrayList<Complement> getComplements() {
        return complements;
    }

    public void setComplements(ArrayList<Complement> complements) {
        this.complements = complements;
    }

    public void addCommentToComplements(List<Comment> complements) {
        this.complements.addAll(complements);
    }

    public void addConversationToComplements(List<Conversation> complements) {
        this.complements.addAll(complements);
    }

    public void removeComplements() {
        complements.removeAll(complements);
    }

    public void retrieveContactNotification(Context context) {
        if (TextUtils.isNumeric(thisContactMD.getValue().getNotification())) {
            NotificationEntity notification = NotificationsEntityManager.getNotification(context,
                    Integer.parseInt(thisContactMD.getValue().getNotification()));
            //Update my cache
            thisContactNotificationMD.postValue(notification);
        }
    }

    public void updateThisContactNotification(Context context, NotificationEntity notification) {
        //Update the broadcast
        deleteNotificationBroadcast(context, notification);
        createNotificationBroadcast(context, notification);
        //Update the storage
        NotificationsEntityManager.saveNotification(context, notification);
        //Update my cache
        thisContactNotificationMD.postValue(notification);
    }

    private void createNotificationBroadcast(Context context, NotificationEntity notification) {
        Notification.scheduleCallReminderNotification(context, notification);
    }

    public void deleteNotificationBroadcast(Context context, NotificationEntity notification) {
        Notification.cancelCallReminderNotification(context, notification);
        //Update the storage
        NotificationsEntityManager.deleteNotification(context, notification);
        //Update my cache
        thisContactNotificationMD.postValue(null);
    }
}
