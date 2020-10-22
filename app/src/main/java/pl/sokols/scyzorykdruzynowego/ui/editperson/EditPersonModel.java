package pl.sokols.scyzorykdruzynowego.ui.editperson;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.UserRepository;

public class EditPersonModel {

    private UserRepository userRepository;

    public EditPersonModel(Application application) {
        this.userRepository = new UserRepository(application);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
