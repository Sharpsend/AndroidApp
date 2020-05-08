package dev.goteam.paydrift.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import dev.goteam.paydrift.db.entities.Transaction;

@Dao
public interface TransactionDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);

    @Query("DELETE FROM `transaction`")
    void deleteAll();

    @Query("SELECT * from `transaction` ORDER BY timeStamp ASC")
    LiveData<List<Transaction>> getAllTransactions();

}
