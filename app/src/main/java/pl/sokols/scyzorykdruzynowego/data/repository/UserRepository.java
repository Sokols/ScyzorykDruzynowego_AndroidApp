package pl.sokols.scyzorykdruzynowego.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.User;
import pl.sokols.scyzorykdruzynowego.data.remote.JsonServerApi;
import pl.sokols.scyzorykdruzynowego.data.remote.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private static UserRepository INSTANCE;
    private static JsonServerApi jsonServerApi;

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    private UserRepository() {
        jsonServerApi = RetrofitService.getServer();
    }

    public LiveData<List<User>> getUsers() {
        final MutableLiveData<List<User>> currentUsers = new MutableLiveData<>();
        jsonServerApi.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    currentUsers.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                currentUsers.postValue(null);
            }
        });

        return currentUsers;
    }

    public void createUser(User user) {
        jsonServerApi.createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // do nothing
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // do nothing
            }
        });
    }
}


//    private static UserRepository INSTANCE;
//    private ExecutorService executorService;
//    private UserDao userDao;
//
//    public static UserRepository getInstance(Application application) {
//        if (INSTANCE == null) {
//            INSTANCE = new UserRepository(application);
//        }
//        return INSTANCE;
//    }
//
//    private UserRepository(@NonNull Application application) {
//        userDao = UsersDatabase.getInstance(application).userDao();
//        executorService = Executors.newSingleThreadExecutor();
//    }
//
//    public void insert(User user) {
//        executorService.execute(() -> userDao.insert(user));
//    }
//
//    public User getItemByName(String login) {
//        return userDao.getItemByName(login);
//    }
//
//    public int checkItemByName(String login) {
//        return userDao.checkItemByName(login);
//    }