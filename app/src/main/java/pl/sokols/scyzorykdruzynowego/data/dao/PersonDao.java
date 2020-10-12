package pl.sokols.scyzorykdruzynowego.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Person;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM people")
    LiveData<List<Person>> getAllPeople();

    @Query("SELECT * FROM people WHERE team = :team")
    List<Person> getPeopleByTeamName(String team);

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person user);
}
