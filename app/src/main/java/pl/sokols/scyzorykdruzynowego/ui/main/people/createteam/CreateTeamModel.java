package pl.sokols.scyzorykdruzynowego.ui.main.people.createteam;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.firebase.FirebaseUtils;
import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;

public class CreateTeamModel {

    private final TeamRepository teamRepository;

    public CreateTeamModel(Application application) {
        this.teamRepository = new TeamRepository(application, FirebaseUtils.getUserId());
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
