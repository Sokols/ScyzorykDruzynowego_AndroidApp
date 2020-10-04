package pl.sokols.scyzorykdruzynowego.activities.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pl.sokols.scyzorykdruzynowego.activities.data.dao.PersonDao;
import pl.sokols.scyzorykdruzynowego.activities.data.dao.UserDao;
import pl.sokols.scyzorykdruzynowego.activities.data.entities.Person;
import pl.sokols.scyzorykdruzynowego.activities.data.entities.User;

@Database(
        entities = {
                Person.class,
                User.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "sd_database.db";

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DB_NAME
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract PersonDao personDao();

    public abstract UserDao userDao();
}
