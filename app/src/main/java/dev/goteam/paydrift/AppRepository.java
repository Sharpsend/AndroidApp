package dev.goteam.paydrift;

import androidx.lifecycle.LiveData;

import java.util.List;

import dev.goteam.paydrift.db.AppDatabase;
import dev.goteam.paydrift.db.entities.Transaction;
import dev.goteam.paydrift.db.entities.User;

public class AppRepository {

    private static AppRepository sInstance;

    private final AppDatabase mDatabase;

    private AppRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static AppRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (AppRepository.class) {
                if (sInstance == null) {
                    sInstance = new AppRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<Transaction>> getTransactions() {
        return mDatabase.transactionDao().getAllTransactions();
    }

    public LiveData<User> getUser() {
        return mDatabase.userDao().getUser();
    }

    public void saveUser(User user) {
        AppDatabase.executors.diskIO().execute(() -> mDatabase.userDao().insert(user));
    }
}
