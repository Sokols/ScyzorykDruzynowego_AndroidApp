package pl.sokols.scyzorykdruzynowego.data.dao;

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
    List<Team> getAllTeams();

    @Query("SELECT EXISTS (SELECT * FROM teams WHERE teamName = :teamName)")
    int checkItemByName(String teamName);
}
