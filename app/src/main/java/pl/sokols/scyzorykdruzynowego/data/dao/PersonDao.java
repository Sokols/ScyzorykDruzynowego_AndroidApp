package pl.sokols.scyzorykdruzynowego.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Person;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM people WHERE team = :team")
    List<Person> getPeopleByTeamName(String team);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person person);

    @Update
    void update(Person person);

    @Query("UPDATE people SET team = :newTeam WHERE team = :oldTeam")
    void updateTeam(String newTeam, String oldTeam);

    @Delete
    void delete(Person user);
}
