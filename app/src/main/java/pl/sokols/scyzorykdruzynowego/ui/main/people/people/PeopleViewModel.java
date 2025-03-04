package pl.sokols.scyzorykdruzynowego.ui.main.people.people;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Team;

public class PeopleViewModel extends AndroidViewModel {

    private final PeopleModel model;
    private LiveData<List<Team>> teamList;

    public PeopleViewModel(@NonNull Application application) {
        super(application);
        this.model = new PeopleModel(application);
    }

    public LiveData<List<Team>> getTeamList() {
        if (teamList == null) {
            teamList = model.getTeamRepository().getAllTeams();
        }
        return teamList;
    }
}
