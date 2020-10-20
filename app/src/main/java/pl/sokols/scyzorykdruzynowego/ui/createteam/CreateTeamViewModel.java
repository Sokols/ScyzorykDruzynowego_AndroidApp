package pl.sokols.scyzorykdruzynowego.ui.createteam;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import pl.sokols.scyzorykdruzynowego.data.entity.Team;

public class CreateTeamViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isReadyToAddTeam = new MutableLiveData<>();
    private MutableLiveData<Boolean> isTeamNameUnique = new MutableLiveData<>();
    private MutableLiveData<Team> team;

    private String teamName;
    private CreateTeamModel model;

    public CreateTeamViewModel(@NonNull Application application) {
        super(application);
        this.model = new CreateTeamModel(application);
    }

    public void handleCreateTeamButton() {
        Team newTeam = new Team(teamName);
        team.setValue(newTeam);
        if (isAllDataCorrect()) {
            model.getTeamRepository().insert(newTeam);
            isReadyToAddTeam.setValue(true);
        }
    }

    private boolean isAllDataCorrect() {
        // check if team name is null
        if (teamName == null) {
            return false;
        }
        // check if team name exists in database
        else if (model.getTeamRepository().checkItemByName(teamName) == 1) {
            isTeamNameUnique.setValue(false);
            return false;
        }

        return true;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public MutableLiveData<Boolean> getIsReadyToAddTeam() {
        return isReadyToAddTeam;
    }

    public MutableLiveData<Boolean> getIsTeamNameUnique() {
        return isTeamNameUnique;
    }

    public MutableLiveData<Team> getTeam() {
        if (team == null) {
            team = new MutableLiveData<>();
        }
        return team;
    }
}
