package pl.sokols.scyzorykdruzynowego.ui.start.login;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.User;

import static pl.sokols.scyzorykdruzynowego.utils.Utils.REMEMBER_ME_SHARED_PREFS_KEY;
import static pl.sokols.scyzorykdruzynowego.utils.Utils.USER_ID_SHARED_PREFS_KEY;
import static pl.sokols.scyzorykdruzynowego.utils.Utils.USER_LOGIN_SHARED_PREFS_KEY;

public class LoginViewModel extends AndroidViewModel {

    private final LoginModel model;

    private final MutableLiveData<Boolean> isPasswordCorrect = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUsernameExists = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isReadyToLogin = new MutableLiveData<>();
    private MutableLiveData<User> user;

    private List<User> currentUsers = new ArrayList<>();

    private String username;
    private String password;
    private boolean rememberMe;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        model = new LoginModel(application);
        model.getUserRepository().getCurrentUsers().observeForever(users -> currentUsers = users);
    }

    public void handleLoginButton() {
        User newUser = new User(username, password);
        user.setValue(newUser);
        if (isAllDataCorrect()) {
            isReadyToLogin.setValue(true);
        }
    }

    private boolean isAllDataCorrect() {
        // get data typed
        String username = getUsername() == null ? "" : getUsername();
        String password = getPassword() == null ? "" : getPassword();

        // check if all data exist
        if (username.equals("") || password.equals("")) {
            return false;
        }
        // check if typed username exists and password is correct
        return checkCorrectnessOfProvidedData(username, password);
    }

    private boolean checkCorrectnessOfProvidedData(String username, String password) {
        for (User user : currentUsers) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    // set shared preferences for chosen user
                    setSharedPreferences(user.getUserId());
                    return true;
                }
                isPasswordCorrect.setValue(false);
                return false;
            }
        }
        isUsernameExists.setValue(false);
        return false;
    }

    private void setSharedPreferences(int userId) {
        SharedPreferences sharedPreferences = model.getSharedPreferences();
        sharedPreferences.edit().putBoolean(REMEMBER_ME_SHARED_PREFS_KEY, isRememberMe()).apply();
        sharedPreferences.edit().putString(USER_LOGIN_SHARED_PREFS_KEY, getUsername()).apply();
        sharedPreferences.edit().putInt(USER_ID_SHARED_PREFS_KEY, userId).apply();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public MutableLiveData<Boolean> getIsPasswordCorrect() {
        return isPasswordCorrect;
    }

    public MutableLiveData<Boolean> getIsUsernameExists() {
        return isUsernameExists;
    }

    public MutableLiveData<Boolean> getIsReadyToLogin() {
        return isReadyToLogin;
    }

    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }
}
