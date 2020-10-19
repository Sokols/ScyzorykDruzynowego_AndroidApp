package pl.sokols.scyzorykdruzynowego.ui.people;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class PeopleModel {

    private TeamRepository teamRepository;
    private PersonRepository personRepository;

    public PeopleModel(Application application) {
        int userId = Utils.getUserId(application);
        this.teamRepository = new TeamRepository(application, userId);
        this.personRepository = new PersonRepository(application, userId);
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }
}
