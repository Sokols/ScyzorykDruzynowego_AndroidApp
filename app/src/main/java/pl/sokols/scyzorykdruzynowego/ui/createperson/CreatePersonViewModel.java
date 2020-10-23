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
    private MutableLiveData<Boolean> isReadyToUpdatePerson = new MutableLiveData<>();
    private MutableLiveData<Person> person;

    private String[] ranks;
    private String[] teams;
    private String[] functions;

    private Person personToSave;
    private String date;
    private boolean isCreatePerson;

    private CreatePersonModel model;

    public CreatePersonViewModel(@NonNull Application application) {
        super(application);
        this.model = new CreatePersonModel(application);
    }

    public void handleCreatePersonButton() {
        addDateToPerson();
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
        // check if name or surname is null
        return personToSave.getName() != null && personToSave.getSurname() != null;
    }

    public Person getPersonToSave() {
        return personToSave;
    }

    public void setCreatePerson(boolean createPerson) {
        isCreatePerson = createPerson;
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

    private void addDateToPerson() {
        Date newDate = null;
        try {
            newDate = Utils.getDateFromString(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        personToSave.setDateOfBirth(newDate);
    }
}
