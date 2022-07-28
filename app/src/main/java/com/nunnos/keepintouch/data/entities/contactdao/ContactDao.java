package com.nunnos.keepintouch.data.entities.contactdao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static com.nunnos.keepintouch.data.entities.contactdao.ContactsDaoConstants.TABLE_NAME;


/**
 * DAO of the Room database.
 */
@Dao
public interface ContactDao {
    @Insert
    void insert(ContactEntity contactEntity);

    @Update
    void update(ContactEntity contactEntity);

    @Query("SELECT * FROM " + TABLE_NAME + " ORDER BY Id DESC")
    List<ContactEntity> getAll();

    @Query("SELECT * FROM " + TABLE_NAME + " ORDER BY lastactionindex DESC")
    List<ContactEntity> getAllOrderByLastIndexDes();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE Id = :id")
    ContactEntity get(int id);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE Id = :id")
    void deleteById(int id);

    @Query("SELECT MIN(lastactionindex) FROM " + TABLE_NAME)
    int getLastIndex();
}
