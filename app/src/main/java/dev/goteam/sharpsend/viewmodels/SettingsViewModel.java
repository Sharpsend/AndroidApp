package dev.goteam.sharpsend.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dev.goteam.sharpsend.AppRepository;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.db.entities.User;

public class SettingsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private AppRepository mRepository;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((SharpsendApp) application).getRepository();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<User> getUser() {
        return mRepository.getUser();
    }

    public void updateUser(User user) {
        mRepository.updateUser(user);
    }
}