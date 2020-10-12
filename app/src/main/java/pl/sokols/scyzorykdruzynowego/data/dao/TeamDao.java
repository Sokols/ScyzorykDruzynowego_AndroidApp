package pl.sokols.scyzorykdruzynowego.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Team;

@Dao
public interface TeamDao {

    @Insert
    void insert(Team team);

    @Query("SELECT * FROM teams ORDER BY team_name ASC")
    LiveData<List<Team>> getAllTeams();

    @Query("SELECT team_name FROM teams ORDER BY team_name ASC")
    List<String> getAllTeamNames();

    @Query("SELECT EXISTS (SELECT * FROM teams WHERE team_name = :teamName)")
    int checkItemByName(String teamName);
}
