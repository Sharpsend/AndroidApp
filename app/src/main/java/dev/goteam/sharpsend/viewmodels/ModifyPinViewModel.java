package dev.goteam.sharpsend.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.goteam.sharpsend.AppRepository;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.db.entities.User;

public class ModifyPinViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    public ModifyPinViewModel(Application application) {
        super(application);
        mRepository = ((SharpsendApp) application).getRepository();
    }

    public void updatePin(User user) {
        mRepository.updateUser(user);
    }

    public LiveData<User> getUser() {
        return mRepository.getUser();
    }

}
