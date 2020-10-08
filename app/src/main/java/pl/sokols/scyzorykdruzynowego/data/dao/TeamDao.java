package pl.sokols.scyzorykdruzynowego.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entities.Team;

@Dao
public interface TeamDao {

    @Insert
    void insert(Team team);

    @Query("SELECT * FROM teams")
    LiveData<List<Team>> getAllTeams();

    @Query("SELECT team_name FROM teams")
    List<String> getAllTeamNames();

    @Query("SELECT EXISTS (SELECT * FROM teams WHERE team_name = :teamName)")
    int checkItemByName(String teamName);
}
