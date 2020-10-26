package pl.sokols.scyzorykdruzynowego.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.sokols.scyzorykdruzynowego.data.dao.TeamDao;
import pl.sokols.scyzorykdruzynowego.data.database.AppDatabase;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;

public class TeamRepository {

    private static TeamRepository INSTANCE;
    private ExecutorService executorService;
    private TeamDao teamDao;

    public static TeamRepository getInstance(Application application, int userId) {
        if (INSTANCE == null) {
            INSTANCE = new TeamRepository(application, userId);
        }
        return INSTANCE;
    }

    private TeamRepository(@NonNull Application application, int userId) {
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
