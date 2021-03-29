package pl.sokols.scyzorykdruzynowego.ui.main.people.people;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.firebase.FirebaseUtils;
import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;

public class PeopleModel {

    private final TeamRepository teamRepository;

    public PeopleModel(Application application) {
        this.teamRepository = new TeamRepository(application, FirebaseUtils.getUserId());
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
