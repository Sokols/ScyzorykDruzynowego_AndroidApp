package pl.sokols.scyzorykdruzynowego.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.sokols.scyzorykdruzynowego.data.dao.UserDao;
import pl.sokols.scyzorykdruzynowego.data.database.UsersDatabase;
import pl.sokols.scyzorykdruzynowego.data.entity.User;

public class UserRepository {

    private ExecutorService executorService;
    private UserDao userDao;

    public UserRepository(@NonNull Application application) {
        userDao = UsersDatabase.getInstance(application).userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public User getItemByName(String login) {
        return userDao.getItemByName(login);
    }

    public int checkItemByName(String login) {
        return userDao.checkItemByName(login);
    }
}