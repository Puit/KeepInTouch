package com.nunnos.keepintouch.data.entities.comment;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nunnos.keepintouch.data.entities.Converters;

@Database(entities = {CommentEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CommentDB extends RoomDatabase {
    public abstract CommentDao commentDao();

    private static CommentDB instance;

    public static synchronized CommentDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CommentDB.class, CommentDaoConstants.TABLE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
