package com.nunnos.keepintouch.data.entities.conversation;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nunnos.keepintouch.data.entities.Converters;

@Database(entities = {ConversationEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ConversationDB extends RoomDatabase {
    public abstract ConversationDao conversationDao();

    private static ConversationDB instance;

    public static synchronized ConversationDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ConversationDB.class, ConversationDaoConstants.TABLE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}