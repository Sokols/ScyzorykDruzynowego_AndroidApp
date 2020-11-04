package pl.sokols.scyzorykdruzynowego.ui.main.people.createperson;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class CreatePersonModel {

    private PersonRepository personRepository;
    private TeamRepository teamRepository;

    public CreatePersonModel(Application application) {
        this.personRepository = new PersonRepository(application, Utils.getUserId(application));
        this.teamRepository = new TeamRepository(application, Utils.getUserId(application));
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
