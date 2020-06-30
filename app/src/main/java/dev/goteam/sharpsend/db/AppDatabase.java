package dev.goteam.sharpsend.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import dev.goteam.sharpsend.AppExecutors;
import dev.goteam.sharpsend.db.dao.TransactionDao;
import dev.goteam.sharpsend.db.dao.UserDao;
import dev.goteam.sharpsend.db.entities.Transaction;
import dev.goteam.sharpsend.db.entities.User;

import static androidx.constraintlayout.widget.Constraints.TAG;

@Database(entities = {User.class, Transaction.class},
        version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    public static final String DATABASE_NAME = "paydrift-db";

    public abstract UserDao userDao();
    public abstract TransactionDao transactionDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    public static AppExecutors executors;

    public static AppDatabase getInstance(final Context context, final AppExecutors mExecutors) {
        executors = mExecutors;
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext) {
        Log.i(TAG, "onCreate: START");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        /*executors.diskIO().execute(() -> {

                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);

                            for (int i = 0; i < 5; i++) {
                                database.transactionDao().insert(new Transaction("Tranfer", (double) 2000,
                                        "Successfull", "1234432"));
                            }
                            Log.i(TAG, "onCreate: DONE");
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });*/
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}
