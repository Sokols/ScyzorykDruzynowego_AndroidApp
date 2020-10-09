package pl.sokols.scyzorykdruzynowego.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import pl.sokols.scyzorykdruzynowego.data.dao.PersonDao;
import pl.sokols.scyzorykdruzynowego.data.dao.TeamDao;
import pl.sokols.scyzorykdruzynowego.data.entities.Person;
import pl.sokols.scyzorykdruzynowego.data.entities.Team;

@Database(entities = {
        Team.class,
        Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String APP_DB_NAME = "app_database.db";
    private static Map<Integer, AppDatabase> instancesMap = new HashMap<>();

    public static synchronized AppDatabase getInstance(Context context, int userId) {
        if (!instancesMap.containsKey(userId)) {

            RoomDatabase.Callback callback = new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Executors.newSingleThreadExecutor().execute(
                            () -> getInstance(context, userId).teamDao().insert(new Team("-")));
                }
            };

            AppDatabase instance = Room.databaseBuilder(context, AppDatabase.class, userId + "_" + APP_DB_NAME)
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    .build();
            instancesMap.put(userId, instance);
        }
        return instancesMap.get(userId);
    }

    public abstract TeamDao teamDao();

    public abstract PersonDao personDao();
}
