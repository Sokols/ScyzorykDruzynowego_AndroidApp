package pl.sokols.scyzorykdruzynowego.ui.people;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class PeopleModel {

    private TeamRepository teamRepository;

    public PeopleModel(Application application) {
        int userId = Utils.getUserId(application);
        this.teamRepository = TeamRepository.getInstance(application, userId);
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
