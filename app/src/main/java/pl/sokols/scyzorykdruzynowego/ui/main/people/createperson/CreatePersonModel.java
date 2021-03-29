package pl.sokols.scyzorykdruzynowego.ui.main.people.createperson;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.firebase.FirebaseUtils;
import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;

public class CreatePersonModel {

    private final PersonRepository personRepository;
    private final TeamRepository teamRepository;

    public CreatePersonModel(Application application) {
        String userId = FirebaseUtils.getUserId();
        this.personRepository = new PersonRepository(application, userId);
        this.teamRepository = new TeamRepository(application, userId);
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
