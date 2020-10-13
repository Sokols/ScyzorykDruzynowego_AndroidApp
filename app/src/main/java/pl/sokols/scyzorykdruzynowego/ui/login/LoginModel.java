package pl.sokols.scyzorykdruzynowego.ui.login;

import android.app.Application;
import android.content.SharedPreferences;

import pl.sokols.scyzorykdruzynowego.data.repository.UserRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class LoginModel {

    private UserRepository userRepository;
    private SharedPreferences sharedPreferences;

    public LoginModel(Application application) {
        this.userRepository = new UserRepository(application);
        this.sharedPreferences = application.getSharedPreferences(Utils.SHARED_PREFS_KEY_NAME, 0);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
