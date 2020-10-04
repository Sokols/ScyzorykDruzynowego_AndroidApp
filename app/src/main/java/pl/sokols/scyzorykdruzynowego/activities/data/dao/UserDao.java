package pl.sokols.scyzorykdruzynowego.activities.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.activities.data.entities.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE username = :username")
    User getItemByName(String username);

    @Query("SELECT EXISTS (SELECT * FROM users WHERE username = :username)")
    int checkItemByName(String username);
}
