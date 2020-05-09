package dev.goteam.paydrift.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dev.goteam.paydrift.AppRepository;
import dev.goteam.paydrift.PaydriftApp;
import dev.goteam.paydrift.db.entities.User;

public class HomeViewModel extends AndroidViewModel {

    private final AppRepository mRepository;
    private final LiveData<User> user;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((PaydriftApp) application).getRepository();
        user = mRepository.getUser();
    }

    public LiveData<User> getUser() {
        return user;
    }
}