package pl.sokols.scyzorykdruzynowego.ui.start.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.User;

public class RegistrationViewModel extends AndroidViewModel {

    private final RegistrationModel model;

    private final MutableLiveData<Boolean> arePasswordsCorrect = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUsernameUnique = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isReadyToRegister = new MutableLiveData<>();
    private MutableLiveData<User> user;

    private List<User> currentUsers = new ArrayList<>();

    private String username;
    private String password;
    private String password2;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        model = new RegistrationModel(application);
        model.getUserRepository().getCurrentUsers().observeForever(users -> currentUsers = users);
    }

    public void handleRegisterButton() {
        User newUser = new User(username, password);
        user.setValue(newUser);
        if (isAllDataCorrect()) {
            model.getUserRepository().createUser(newUser);
            isReadyToRegister.setValue(true);
        }
    }

    private boolean isAllDataCorrect() {
        // get data typed
        String username = getUsername() == null ? "" : getUsername();
        String password = getPassword() == null ? "" : getPassword();
        String password2 = getPassword2() == null ? "" : getPassword2();

        // check if all data exist
        if (username.equals("") || password.equals("") || password2.equals("")) {
            return false;
        }
        // check if typed username exists in database
        for (User user : currentUsers) {
            if (user.getUsername().equals(username)) {
                isUsernameUnique.setValue(false);
                return false;
            }
        }
        // check if password equals password2
        if (!password.equals(password2)) {
            arePasswordsCorrect.setValue(false);
            return false;
        }

        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public MutableLiveData<Boolean> getArePasswordsCorrect() {
        return arePasswordsCorrect;
    }

    public MutableLiveData<Boolean> getIsUsernameUnique() {
        return isUsernameUnique;
    }

    public MutableLiveData<Boolean> getIsReadyToRegister() {
        return isReadyToRegister;
    }

    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }
}
