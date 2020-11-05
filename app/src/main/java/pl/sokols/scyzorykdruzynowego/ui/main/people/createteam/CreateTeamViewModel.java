package pl.sokols.scyzorykdruzynowego.ui.main.people.createteam;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import pl.sokols.scyzorykdruzynowego.data.entity.Team;

public class CreateTeamViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isReadyToAddTeam = new MutableLiveData<>();
    private MutableLiveData<Boolean> isReadyToUpdateTeam = new MutableLiveData<>();
    private MutableLiveData<Boolean> isTeamNameUnique = new MutableLiveData<>();
    private MutableLiveData<Team> team;

    private Team teamToSave;
    private boolean isCreateTeam;

    private CreateTeamModel model;

    public CreateTeamViewModel(@NonNull Application application) {
        super(application);
        this.model = new CreateTeamModel(application);
    }

    public void handleCreateTeamButton() {
        team.setValue(teamToSave);
        if (isAllDataCorrect()) {
            if (isCreateTeam) {
                model.getTeamRepository().insert(teamToSave);
                isReadyToAddTeam.setValue(true);
            } else {
                model.getTeamRepository().update(teamToSave);
                isReadyToUpdateTeam.setValue(true);
            }
        }
    }

    private boolean isAllDataCorrect() {
        // check if team name is null
        if (teamToSave == null
                || teamToSave.getTeamName() == null
                || teamToSave.getTeamName().equals("")) {
            return false;
        }
        // check if team name exists in database
        else if (model.getTeamRepository().checkItemByName(teamToSave.getTeamName()) == 1) {
            isTeamNameUnique.setValue(false);
            return false;
        }

        return true;
    }

    public Team getTeamToSave() {
        return teamToSave;
    }

    public void setTeamToSave(Team teamToSave) {
        this.teamToSave = teamToSave;
    }

    public void setCreateTeam(boolean createTeam) {
        isCreateTeam = createTeam;
    }

    public MutableLiveData<Boolean> getIsReadyToAddTeam() {
        return isReadyToAddTeam;
    }

    public MutableLiveData<Boolean> getIsReadyToUpdateTeam() {
        return isReadyToUpdateTeam;
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
