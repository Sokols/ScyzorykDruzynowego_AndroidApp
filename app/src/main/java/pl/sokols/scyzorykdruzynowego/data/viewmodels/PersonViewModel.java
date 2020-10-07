package pl.sokols.scyzorykdruzynowego.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.sokols.scyzorykdruzynowego.data.AppDatabase;
import pl.sokols.scyzorykdruzynowego.data.dao.PersonDao;
import pl.sokols.scyzorykdruzynowego.data.entities.Person;

public class PersonViewModel extends AndroidViewModel {

    private ExecutorService executorService;
    private PersonDao personDao;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        personDao = AppDatabase.getInstance(application).personDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Person>> getAllPeople() {
        return personDao.getAllPeople();
    }

    public void insert(Person person) {
        executorService.execute(() -> personDao.insert(person));
    }

    public void delete(Person person) {
        executorService.execute(() -> personDao.delete(person));
    }
}
