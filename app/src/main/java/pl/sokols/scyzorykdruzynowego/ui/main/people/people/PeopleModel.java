package pl.sokols.scyzorykdruzynowego.ui.main.people.people;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class PeopleModel {

    private TeamRepository teamRepository;

    public PeopleModel(Application application) {
        int userId = Utils.getUserId(application);
        this.teamRepository = new TeamRepository(application, userId);
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
