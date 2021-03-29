package pl.sokols.scyzorykdruzynowego.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.sokols.scyzorykdruzynowego.data.dao.PersonDao;
import pl.sokols.scyzorykdruzynowego.data.database.AppDatabase;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;

public class PersonRepository {

    private final ExecutorService executorService;
    private final PersonDao personDao;

    public PersonRepository(@NonNull Application application, String userId) {
        personDao = AppDatabase.getInstance(application, userId).personDao();
        executorService = Executors.newSingleThreadExecutor();
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

    public void updateTeam(String newTeamName, String oldTeamName) {
        executorService.execute(() -> personDao.updateTeam(newTeamName, oldTeamName));
    }

    public void delete(Person person) {
        executorService.execute(() -> personDao.delete(person));
    }
}
