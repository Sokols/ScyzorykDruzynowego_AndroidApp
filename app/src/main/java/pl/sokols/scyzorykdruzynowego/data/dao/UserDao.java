package pl.sokols.scyzorykdruzynowego.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import pl.sokols.scyzorykdruzynowego.data.entity.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username")
    User getItemByName(String username);

    @Query("SELECT EXISTS (SELECT * FROM users WHERE username = :username)")
    int checkItemByName(String username);
}
