package dev.goteam.sharpsend.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import dev.goteam.sharpsend.db.entities.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteUser();

    @Query("SELECT * from user")
    LiveData<User> getUser();

    @Update
    void saveDefaultSim(User user);

    @Update
    void updateUser(User user);
}
