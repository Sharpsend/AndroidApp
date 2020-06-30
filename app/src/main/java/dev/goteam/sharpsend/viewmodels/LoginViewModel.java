package dev.goteam.sharpsend.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.goteam.sharpsend.AppRepository;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.utils.Prefs;

public class LoginViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;

    public LoginViewModel(Application application) {
        super(application);
        mAppRepository = ((SharpsendApp) application).getRepository();
    }

    public boolean isFingerPrintIsEnabled() {
        return Prefs.isFingerprintEnabled(getApplication().getBaseContext());
    }

    public LiveData<User> getUser() {
        return mAppRepository.getUser();
    }
}
