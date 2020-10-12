package pl.sokols.scyzorykdruzynowego.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pl.sokols.scyzorykdruzynowego.data.dao.UserDao;
import pl.sokols.scyzorykdruzynowego.data.entity.User;

@Database(entities = {
                User.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {

    private static final String USER_DB_NAME = "user_database.db";
    private static UsersDatabase INSTANCE;

    public static synchronized UsersDatabase getInstance(Context context) {
        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context, UsersDatabase.class, USER_DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
}
