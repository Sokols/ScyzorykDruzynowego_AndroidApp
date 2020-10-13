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

import pl.sokols.scyzorykdruzynowego.data.dao.TeamDao;
import pl.sokols.scyzorykdruzynowego.data.database.AppDatabase;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;

public class TeamViewModel extends AndroidViewModel {

    public static class TeamViewModelFactory implements ViewModelProvider.Factory {

        private Application mApplication;
        private int mUserIdParam;

        public TeamViewModelFactory(@NonNull Application application, int userIdParam) {
            this.mApplication = application;
            this.mUserIdParam = userIdParam;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TeamViewModel(mApplication, mUserIdParam);
        }
    }

    private ExecutorService executorService;
    private TeamDao teamDao;

    public TeamViewModel(@NonNull Application application, int userId) {
        super(application);
        teamDao = AppDatabase.getInstance(application, userId).teamDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Team>> getAllTeams() {
        return teamDao.getAllTeams();
    }

    public List<String> getAllTeamNames() {
        return teamDao.getAllTeamNames();
    }

    public void insert(Team team) {
        executorService.execute(() -> teamDao.insert(team));
    }

    public int checkItemByName(String teamName) {
        return teamDao.checkItemByName(teamName);
    }
}
