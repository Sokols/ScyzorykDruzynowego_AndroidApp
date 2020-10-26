package pl.sokols.scyzorykdruzynowego.ui.createteam;

import android.app.Application;

import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class CreateTeamModel {

    private TeamRepository teamRepository;

    public CreateTeamModel(Application application) {
        this.teamRepository = TeamRepository.getInstance(application, Utils.getUserId(application));
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
