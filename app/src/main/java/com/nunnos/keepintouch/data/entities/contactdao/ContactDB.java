package com.nunnos.keepintouch.data.entities.contactdao;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nunnos.keepintouch.data.entities.Converters;

@Database(entities = {ContactEntity.class}, version = 1, exportSchema = true,
        autoMigrations = {
                @AutoMigration(from = 1, to = 2)
        })
@TypeConverters({Converters.class})
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactDao contactDao();

    private static ContactDB instance;

    public static synchronized ContactDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDB.class, ContactsDaoConstants.TABLE_NAME)
                    .build();
        }
        return instance;
    }
    //TODO: Exemple de migració d'una taula
    /*static final Migration MIGRATION_6_7= new Migration(6, 7) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");
        }
    };*/
}
