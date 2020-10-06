package pl.sokols.scyzorykdruzynowego.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pl.sokols.scyzorykdruzynowego.data.dao.PersonDao;
import pl.sokols.scyzorykdruzynowego.data.dao.TeamDao;
import pl.sokols.scyzorykdruzynowego.data.dao.UserDao;
import pl.sokols.scyzorykdruzynowego.data.entities.Person;
import pl.sokols.scyzorykdruzynowego.data.entities.Team;
import pl.sokols.scyzorykdruzynowego.data.entities.User;

@Database(
        entities = {
                Person.class,
                User.class,
                Team.class},
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

    public abstract TeamDao teamDao();
}
