package com.nunnos.keepintouch.domain;

import com.nunnos.keepintouch.data.entities.contactdao.ContactDao;
import com.nunnos.keepintouch.data.entities.contactdao.ContactEntity;

public abstract class DBManager implements ContactDao {

/*    public static void insertContact(ContactEntity contactEntity){
        AppExecutors.getInstance().diskIO().execute(() -> {
            ContactDao.insert(contactEntity);
        });
    }
    public static void get(int id){
        AppExecutors.getInstance().diskIO().execute(() -> {
            ContactEntity contact = ContactDao.get(id);
        });
    }*/
}
