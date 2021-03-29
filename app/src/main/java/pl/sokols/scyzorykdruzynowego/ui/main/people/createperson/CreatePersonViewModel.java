package pl.sokols.scyzorykdruzynowego.ui.main.people.createperson;

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

    private final MutableLiveData<Boolean> isReadyToAddPerson = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isReadyToUpdatePerson = new MutableLiveData<>();
    private MutableLiveData<Person> person;

    private Person personToSave;
    private String date;
    private boolean isCreatePerson;

    private final CreatePersonModel model;

    public CreatePersonViewModel(@NonNull Application application) {
        super(application);
        this.model = new CreatePersonModel(application);
    }

    public void handleCreatePersonButton() {
        preparePerson();
        person.setValue(personToSave);
        if (isAllDataCorrect()) {
            if (isCreatePerson) {
                model.getPersonRepository().insert(personToSave);
                isReadyToAddPerson.setValue(true);
            } else {
                model.getPersonRepository().update(personToSave);
                isReadyToUpdatePerson.setValue(true);
            }
        }
    }

    private boolean isAllDataCorrect() {
        // check if name or surname is null o ""
        if (personToSave.getName() != null && personToSave.getSurname() != null) {
            return !personToSave.getName().equals("") && !personToSave.getSurname().equals("");
        }
        return false;
    }

    public void setCreatePerson(boolean createPerson) {
        isCreatePerson = createPerson;
    }

    public Person getPersonToSave() {
        return personToSave;
    }

    public void setPersonToSave(Person personToSave) {
        this.personToSave = personToSave;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getRanks() {
        return getApplication().getResources().getStringArray(R.array.ranks);
    }

    public String[] getTeams() {
        return model.getTeamRepository().getAllTeamNames().toArray(new String[0]);
    }

    public String[] getFunctions() {
        return getApplication().getResources().getStringArray(R.array.functions);
    }

    public MutableLiveData<Boolean> getIsReadyToAddPerson() {
        return isReadyToAddPerson;
    }

    public MutableLiveData<Boolean> getIsReadyToUpdatePerson() {
        return isReadyToUpdatePerson;
    }

    public MutableLiveData<Person> getPerson() {
        if (person == null) {
            person = new MutableLiveData<>();
        }
        return person;
    }

    public void setPerson(MutableLiveData<Person> person) {
        this.person = person;
    }

    private void preparePerson() {
        // prepare person date
        Date newDate = null;
        try {
            newDate = Utils.getDateFromString(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        personToSave.setDateOfBirth(newDate);

        // prepare person rank, team and function
        if (personToSave.getRank() == null) {
            personToSave.setRank("-");
        }

        if (personToSave.getTeam() == null) {
            personToSave.setTeam("-");
        }

        if (personToSave.getFunction() == null) {
            personToSave.setFunction("-");
        }
    }
}
