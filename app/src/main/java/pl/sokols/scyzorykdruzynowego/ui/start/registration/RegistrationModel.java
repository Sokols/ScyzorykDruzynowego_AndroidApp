package pl.sokols.scyzorykdruzynowego.ui.start.registration;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.UserRepository;

public class RegistrationModel {

    private UserRepository userRepository;

    public RegistrationModel(Application application) {
        this.userRepository = UserRepository.getInstance(application);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
