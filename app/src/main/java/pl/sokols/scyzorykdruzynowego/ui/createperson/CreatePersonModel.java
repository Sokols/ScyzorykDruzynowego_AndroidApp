package pl.sokols.scyzorykdruzynowego.ui.createperson;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class CreatePersonModel {

    private PersonRepository personRepository;
    private TeamRepository teamRepository;

    public CreatePersonModel(Application application) {
        this.personRepository = PersonRepository.getInstance(application, Utils.getUserId(application));
        this.teamRepository = TeamRepository.getInstance(application, Utils.getUserId(application));
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
