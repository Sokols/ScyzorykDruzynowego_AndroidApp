package pl.sokols.scyzorykdruzynowego.data.firebase;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import pl.sokols.scyzorykdruzynowego.R;

public class FirebaseAuthRepository {
    private final Application application;
    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final MutableLiveData<Boolean> loggedOutLiveData;
    private final MutableLiveData<String> errorMessageLiveData;

    public FirebaseAuthRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        this.errorMessageLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        } else {
            userLiveData.postValue(null);
        }
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), this::handleTask);
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), this::handleTask);
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    private void handleTask(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
        } else {
            String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();
            String errorMessage;
            switch (errorCode) {
                case "ERROR_INVALID_EMAIL":
                    errorMessage = application.getString(R.string.ERROR_INVALID_EMAIL);
                    break;

                case "ERROR_WRONG_PASSWORD":
                    errorMessage = application.getString(R.string.ERROR_WRONG_PASSWORD);
                    break;

                case "ERROR_EMAIL_ALREADY_IN_USE":
                    errorMessage = application.getString(R.string.ERROR_EMAIL_ALREADY_IN_USE);
                    break;

                case "ERROR_USER_NOT_FOUND":
                    errorMessage = application.getString(R.string.ERROR_USER_NOT_FOUND);
                    break;

                case "ERROR_WEAK_PASSWORD":
                    errorMessage = application.getString(R.string.ERROR_WEAK_PASSWORD);
                    break;

                default:
                    errorMessage = application.getString(R.string.ERROR);
            }
            errorMessageLiveData.postValue(errorMessage);
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }
}
