package dev.goteam.paydrift;

import android.app.Application;

import dev.goteam.paydrift.db.AppDatabase;

public class PaydriftApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public AppRepository getRepository() {
        return AppRepository.getInstance(getDatabase());
    }
}
