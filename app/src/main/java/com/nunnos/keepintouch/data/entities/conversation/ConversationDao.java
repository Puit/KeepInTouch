package com.nunnos.keepintouch.data.entities.conversation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static com.nunnos.keepintouch.data.entities.conversation.ConversationDaoConstants.TABLE_NAME;

/**
 * DAO of the Room database.
 */
@Dao
public interface ConversationDao {
    @Insert
    void insert(ConversationEntity conversationEntity);

    @Update
    void update(ConversationEntity conversationEntity);

    @Query("SELECT * FROM " + TABLE_NAME + " ORDER BY Id DESC")
    List<ConversationEntity> getAll();

    @Query("DELETE FROM " + TABLE_NAME + " WHERE Id = :id")
    void deleteById(int id);

    //TODO: PROBAR
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + ".contacts LIKE :userId ORDER BY Date DESC")
    List<ConversationEntity> getAllFromContactId(String userId); //DEBE USARSE CON COMMA + ID + COMMA
}
