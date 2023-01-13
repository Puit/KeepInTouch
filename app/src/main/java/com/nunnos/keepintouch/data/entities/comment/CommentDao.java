package com.nunnos.keepintouch.data.entities.comment;


import static com.nunnos.keepintouch.data.entities.comment.CommentDaoConstants.TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

/**
 * DAO of the Room database.
 */
@Dao
public interface CommentDao {
    @Insert
    void insert(CommentEntity commentEntity);

    @Update
    void update(CommentEntity commentEntity);

    @Query("SELECT * FROM " + TABLE_NAME + " ORDER BY Id DESC")
    List<CommentEntity> getAll();

    @Query("DELETE FROM " + TABLE_NAME + " WHERE Id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + ".contactId LIKE :userId ORDER BY Date DESC")
    List<CommentEntity> getAllFromWhoToldId(int userId); //DEBE USARSE ID

    //TODO: PROBAR, per borrar tots els chats que només influeixin a un usuari quan aquest es borrat
    @Query("DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + ".whoTold IS :userId")
    void deleteAllFromUser(String userId); //DEBE USARSE CON COMMA + ID + COMMA
}
