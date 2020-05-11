package dev.goteam.sharpsend.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dev.goteam.sharpsend.AppRepository;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.db.entities.Transaction;

public class TransactionsViewModel extends AndroidViewModel {

    private final AppRepository mRepository;
    private LiveData<List<Transaction>> transactions;

    public TransactionsViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((SharpsendApp) application).getRepository();
        transactions = mRepository.getTransactions();
    }


    public LiveData<List<Transaction>> getTransactions () {
        return transactions;
    }
}