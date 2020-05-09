package dev.goteam.sharpsend.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.goteam.sharpsend.AppRepository;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.db.entities.User;

public class HomeViewModel extends AndroidViewModel {

    private final AppRepository mRepository;
    private final LiveData<User> user;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((SharpsendApp) application).getRepository();
        user = mRepository.getUser();
    }

    public LiveData<User> getUser() {
        return user;
    }
}