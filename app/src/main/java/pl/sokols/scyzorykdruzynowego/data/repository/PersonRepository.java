package pl.sokols.scyzorykdruzynowego.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.sokols.scyzorykdruzynowego.data.dao.PersonDao;
import pl.sokols.scyzorykdruzynowego.data.database.AppDatabase;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;

public class PersonRepository {

    private ExecutorService executorService;
    private PersonDao personDao;

    public PersonRepository(@NonNull Application application, int userId) {
        personDao = AppDatabase.getInstance(application, userId).personDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Person>> getAllPeople() {
        return personDao.getAllPeople();
    }

    public List<Person> getPeopleByTeamName(String teamName) {
        return personDao.getPeopleByTeamName(teamName);
    }

    public void insert(Person person) {
        executorService.execute(() -> personDao.insert(person));
    }

    public void update(Person person) {
        executorService.execute(() -> personDao.update(person));
    }

    public void delete(Person person) {
        executorService.execute(() -> personDao.delete(person));
    }
}
