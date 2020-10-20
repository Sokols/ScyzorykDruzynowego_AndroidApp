package pl.sokols.scyzorykdruzynowego.ui.createperson;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.util.Date;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class CreatePersonViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isReadyToAddPerson = new MutableLiveData<>();
    private MutableLiveData<Person> person;

    private String[] ranks;
    private String[] teams;
    private String[] functions;

    private String name;
    private String surname;
    private String date;
    private String rank;
    private String team;
    private String function;
    private CreatePersonModel model;

    public CreatePersonViewModel(@NonNull Application application) {
        super(application);
        this.model = new CreatePersonModel(application);
    }

    public void handleCreatePersonButton() {
        Person newPerson = getNewPerson();
        person.setValue(newPerson);
        if (isAllDataCorrect()) {
            model.getPersonRepository().insert(newPerson);
            isReadyToAddPerson.setValue(true);
        }
    }

    private boolean isAllDataCorrect() {
        // check if name or surname is null
        return name != null && surname != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String[] getRanks() {
        if (ranks == null) {
            ranks = getApplication().getResources().getStringArray(R.array.ranks);
        }
        return ranks;
    }

    public String[] getTeams() {
        teams = model.getTeamRepository().getAllTeamNames().toArray(new String[0]);
        return teams;
    }

    public String[] getFunctions() {
        if (functions == null) {
            functions = getApplication().getResources().getStringArray(R.array.functions);
        }
        return functions;
    }

    public MutableLiveData<Boolean> getIsReadyToAddPerson() {
        return isReadyToAddPerson;
    }

    public MutableLiveData<Person> getPerson() {
        if (person == null) {
            person = new MutableLiveData<>();
        }
        return person;
    }

    private Person getNewPerson() {
        Date newDate = null;
        try {
            newDate = Utils.getDateFromString(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return new Person(
                name,
                surname,
                newDate,
                rank == null ? getApplication().getString(R.string.blank) : rank,
                team == null ? getApplication().getString(R.string.blank) : team,
                function == null ? getApplication().getString(R.string.blank) : function);
    }
}
