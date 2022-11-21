package com.nunnos.keepintouch.data.entities.contactdao;

import static com.nunnos.keepintouch.data.entities.contactdao.ContactsDaoConstants.TABLE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nunnos.keepintouch.data.entities.Converters;

@Database(entities = {ContactEntity.class}, version = ContactDB.LATEST_VERSION, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class ContactDB extends RoomDatabase {
    public final static int LATEST_VERSION = 2;

    public abstract ContactDao contactDao();

    private static ContactDB instance;

    public static synchronized ContactDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDB.class, ContactsDaoConstants.TABLE_NAME)
                    .addMigrations(MIGRATION_1_2)
//                    .addMigrations(MIGRATION_1_2,MIGRATION_2_3) //Així seria si hi ha més d'un
                    .build();
        }
        return instance;
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN 'socialmedia' TEXT");
        }
    };
}
