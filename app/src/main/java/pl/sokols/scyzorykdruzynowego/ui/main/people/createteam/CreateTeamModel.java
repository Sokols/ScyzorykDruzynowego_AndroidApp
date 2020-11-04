package pl.sokols.scyzorykdruzynowego.ui.main.people.createteam;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class CreateTeamModel {

    private TeamRepository teamRepository;

    public CreateTeamModel(Application application) {
        this.teamRepository = new TeamRepository(application, Utils.getUserId(application));
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
