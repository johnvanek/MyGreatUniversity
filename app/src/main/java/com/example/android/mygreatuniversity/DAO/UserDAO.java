package com.example.android.mygreatuniversity.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.mygreatuniversity.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users")
    List<User> getUsers();

    @Query("SELECT * FROM users WHERE userID LIKE :userID LIMIT 1")
    User findUserById(int userID);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
