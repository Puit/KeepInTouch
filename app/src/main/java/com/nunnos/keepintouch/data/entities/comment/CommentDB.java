package com.nunnos.keepintouch.data.entities.comment;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nunnos.keepintouch.data.entities.Converters;

@Database(entities = {CommentEntity.class}, version = CommentDB.LATEST_VERSION, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class CommentDB extends RoomDatabase {
    public final static int LATEST_VERSION = 2;
    public abstract CommentDao commentDao();

    private static CommentDB instance;

    public static synchronized CommentDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CommentDB.class, CommentDaoConstants.TABLE_NAME)
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE " + CommentDaoConstants.TABLE_NAME + " ADD COLUMN 'contactId' INTEGER NOT NULL DEFAULT -1");
        }
    };
}
