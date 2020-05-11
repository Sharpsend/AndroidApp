package dev.goteam.sharpsend.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import dev.goteam.sharpsend.utils.Prefs;

public class LoginViewModel extends AndroidViewModel {

    private String[] userData;

    public LoginViewModel(Application application) {
        super(application);
        userData = Prefs.getUserData(application.getApplicationContext());
    }

    public String getUsername() {
        return userData[1];
    }

    public boolean isFingerPrintIsEnabled() {
        return Prefs.isFingerprintEnabled(getApplication().getBaseContext());
    }

    public String getPin() {
        return userData[0];
    }
}
