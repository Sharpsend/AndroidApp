package dev.goteam.sharpsend;

import android.app.Application;

import dev.goteam.sharpsend.db.AppDatabase;

public class SharpsendApp extends Application {

    public static final String SUCCESSFUL_TX = "Successful";
    public static final String FAILED_TX = "Failed";
    public static final String PENDING_TX = "Pending";

    public static final String BANK_TRANSFER = "BankTransfer";
    public static final String BANK_RECHARGE = "BankRecharge";
    public static final String SIM_AIRTIME = "SimAirtime";

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public AppRepository getRepository() {
        return AppRepository.getInstance(getDatabase());
    }
}
