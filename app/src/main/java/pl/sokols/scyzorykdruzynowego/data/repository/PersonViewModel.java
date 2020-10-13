package pl.sokols.scyzorykdruzynowego.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.sokols.scyzorykdruzynowego.data.dao.PersonDao;
import pl.sokols.scyzorykdruzynowego.data.database.AppDatabase;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;

public class PersonViewModel extends AndroidViewModel {

    public static class PersonViewModelFactory implements ViewModelProvider.Factory {

        private Application mApplication;
        private int mUserIdParam;

        public PersonViewModelFactory(Application application, int userIdParam) {
            this.mApplication = application;
            this.mUserIdParam = userIdParam;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PersonViewModel(mApplication, mUserIdParam);
        }
    }

    private ExecutorService executorService;
    private PersonDao personDao;

    public PersonViewModel(@NonNull Application application, int userId) {
        super(application);
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
