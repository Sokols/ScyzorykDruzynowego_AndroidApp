package pl.sokols.scyzorykdruzynowego.ui.login;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import pl.sokols.scyzorykdruzynowego.data.entity.User;
import pl.sokols.scyzorykdruzynowego.data.repository.UserRepository;

import static pl.sokols.scyzorykdruzynowego.utils.Utils.REMEMBER_ME_SHARED_PREFS_KEY;
import static pl.sokols.scyzorykdruzynowego.utils.Utils.USER_ID_SHARED_PREFS_KEY;
import static pl.sokols.scyzorykdruzynowego.utils.Utils.USER_LOGIN_SHARED_PREFS_KEY;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isPasswordCorrect = new MutableLiveData<>();
    private MutableLiveData<Boolean> isUsernameUnique = new MutableLiveData<>();
    private MutableLiveData<Boolean> isReadyToLogin = new MutableLiveData<>();
    private MutableLiveData<User> user;

    private String username;
    private String password;
    private boolean rememberMe;

    private LoginModel model;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.model = new LoginModel(application);
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

        UserRepository userRepository = model.getUserRepository();

        // check if all data exist
        if (username.equals("") || password.equals("")) {
            return false;
        }
        // check if typed username exists in database
        else if (userRepository.checkItemByName(username) != 1) {
            isUsernameUnique.setValue(false);
            return false;
        }
        // check if password is correct
        else if (!userRepository.getItemByName(username).getPassword().equals(password)) {
            isPasswordCorrect.setValue(false);
            return false;
        }

        setSharedPreferences(userRepository.getItemByName(username).getUserId());
        return true;
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

    public MutableLiveData<Boolean> getIsUsernameUnique() {
        return isUsernameUnique;
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
