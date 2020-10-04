package pl.sokols.scyzorykdruzynowego.activities.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.activities.data.entities.Person;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM people")
    List<Person> getAll();

    @Insert
    void insert(Person person);

    @Delete
    void delete(Person user);
}
