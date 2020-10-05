package pl.sokols.scyzorykdruzynowego.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entities.Person;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM people")
    List<Person> getAllPeople();

    @Insert
    void insert(Person person);

    @Delete
    void delete(Person user);
}
