package dev.goteam.paydrift.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dev.goteam.paydrift.AppRepository;
import dev.goteam.paydrift.PaydriftApp;
import dev.goteam.paydrift.db.entities.Transaction;

public class TransactionsViewModel extends AndroidViewModel {

    private final AppRepository mRepository;
    private LiveData<List<Transaction>> transactions;

    public TransactionsViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((PaydriftApp) application).getRepository();
        transactions = mRepository.getTransactions();
    }


    public LiveData<List<Transaction>> getTransactions () {
        return transactions;
    }
}