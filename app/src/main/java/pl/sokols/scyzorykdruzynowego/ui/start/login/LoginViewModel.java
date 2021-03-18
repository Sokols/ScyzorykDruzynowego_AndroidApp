package pl.sokols.scyzorykdruzynowego.ui.start.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.repository.AuthRepository;

public class LoginViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final MutableLiveData<String> errorMessageLiveData;

    private String username;
    private String password;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        userLiveData = authRepository.getUserLiveData();
        errorMessageLiveData = authRepository.getErrorMessageLiveData();
    }

    public void login(String email, String password) {
        authRepository.login(email, password);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public void handleLoginButton() {
        if (username.length() > 0 && password.length() > 0) {
            login(username, password);
        } else {
            getApplication().getString(R.string.ERROR_BLANK_INPUTS);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }
}
