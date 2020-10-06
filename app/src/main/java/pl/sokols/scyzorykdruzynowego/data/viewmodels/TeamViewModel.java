package pl.sokols.scyzorykdruzynowego.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.sokols.scyzorykdruzynowego.data.AppDatabase;
import pl.sokols.scyzorykdruzynowego.data.dao.TeamDao;
import pl.sokols.scyzorykdruzynowego.data.entities.Team;

public class TeamViewModel extends AndroidViewModel {

    private ExecutorService executorService;
    private TeamDao teamDao;

    public TeamViewModel(@NonNull Application application) {
        super(application);
        teamDao = AppDatabase.getInstance(application).teamDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Team team) {
        executorService.execute(() -> teamDao.insert(team));
    }

    public List<Team> getAllTeams() {
        return teamDao.getAllTeams();
    }

    public int checkItemByName(String teamName) {
        return teamDao.checkItemByName(teamName);
    }
}
