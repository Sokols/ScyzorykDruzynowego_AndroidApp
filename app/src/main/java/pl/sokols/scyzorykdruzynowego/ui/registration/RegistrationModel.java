package pl.sokols.scyzorykdruzynowego.ui.registration;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.UserRepository;

public class RegistrationModel {

    private UserRepository userRepository;

    public RegistrationModel(Application application) {
        this.userRepository = new UserRepository(application);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
