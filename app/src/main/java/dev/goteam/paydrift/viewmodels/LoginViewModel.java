package dev.goteam.paydrift.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import dev.goteam.paydrift.utils.Prefs;

public class LoginViewModel extends AndroidViewModel {

    private String[] userData;
    private boolean fingerPrintIsEnabled = true;

    public LoginViewModel(Application application) {
        super(application);
        userData = Prefs.getUserData(application.getApplicationContext());
    }

    public String getUsername() {
        return userData[1];
    }

    public boolean isFingerPrintIsEnabled() {
        return fingerPrintIsEnabled;
    }

    public String getPin() {
        return userData[0];
    }
}
