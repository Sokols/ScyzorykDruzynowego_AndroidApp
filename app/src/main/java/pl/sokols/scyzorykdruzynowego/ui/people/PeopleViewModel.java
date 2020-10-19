package pl.sokols.scyzorykdruzynowego.ui.people;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;

public class PeopleViewModel extends AndroidViewModel {

    private LiveData<List<Person>> peopleByTeamList;
    private LiveData<List<Team>> teamList;

    private PeopleModel model;

    public PeopleViewModel(@NonNull Application application) {
        super(application);
        this.model = new PeopleModel(application);
    }

    public LiveData<List<Person>> getPeopleByTeamList() {
        if (peopleByTeamList == null) {
            peopleByTeamList = model.getPersonRepository().getAllPeople();
        }
        return peopleByTeamList;
    }

    public LiveData<List<Team>> getTeamList() {
        if (teamList == null) {
            teamList = model.getTeamRepository().getAllTeams();
        }
        return teamList;
    }
}
