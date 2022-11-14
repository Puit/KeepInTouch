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

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE instr(contacts, :userId) ORDER BY Date DESC")
    List<ConversationEntity> getAllFromContactId(String userId); //DEBE USARSE CON COMMA + ID + COMMA

    //TODO: PROBAR, per borrar tots els chats que només influeixin a un usuari quan aquest es borrat
    @Query("DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + ".contacts IS :userId")
    void deleteAllFromUser(String userId); //DEBE USARSE CON COMMA + ID + COMMA
}
