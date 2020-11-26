package pl.sokols.scyzorykdruzynowego.data.repository;

import androidx.annotation.NonNull;
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
    private MutableLiveData<List<User>> currentUsers;

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    private UserRepository() {
        jsonServerApi = RetrofitService.getServer();
    }

    public void getUsers() {
        jsonServerApi.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    currentUsers.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                currentUsers.postValue(null);
                t.printStackTrace();
            }
        });
    }

    public void createUser(User user) {
        jsonServerApi.createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    getUsers();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                // do nothing
            }
        });
    }

    public MutableLiveData<List<User>> getCurrentUsers() {
        if (currentUsers == null) {
            currentUsers = new MutableLiveData<>();
        }
        getUsers();
        return currentUsers;
    }
}
