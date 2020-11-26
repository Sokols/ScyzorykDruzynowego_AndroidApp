package pl.sokols.scyzorykdruzynowego.ui.main.people.people;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class PeopleModel {

    private final TeamRepository teamRepository;

    public PeopleModel(Application application) {
        this.teamRepository = new TeamRepository(application, Utils.getUserId(application));
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
