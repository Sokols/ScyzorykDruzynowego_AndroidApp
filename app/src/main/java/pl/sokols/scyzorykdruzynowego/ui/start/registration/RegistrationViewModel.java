package pl.sokols.scyzorykdruzynowego.ui.start.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.repository.AuthRepository;

public class RegistrationViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<String> errorMessageLiveData;
    private final MutableLiveData<FirebaseUser> userLiveData;

    private String username;
    private String password;
    private String password2;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        userLiveData = authRepository.getUserLiveData();
        errorMessageLiveData = authRepository.getErrorMessageLiveData();
    }

    public void register(String email, String password) {
        authRepository.register(email, password);
    }

    public void handleRegisterButton() {
        if (!password.equals(password2)) {
            errorMessageLiveData.postValue(getApplication().getString(R.string.ERROR_DIFFERENT_PASSWORDS));
        } else if (username.length() > 0 && password.length() > 0) {
            register(username, password);
        } else {
           errorMessageLiveData.postValue(getApplication().getString(R.string.ERROR_BLANK_INPUTS));
        }
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

    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
