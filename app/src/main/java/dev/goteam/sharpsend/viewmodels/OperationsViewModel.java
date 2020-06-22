package dev.goteam.sharpsend.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

import dev.goteam.sharpsend.AppRepository;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.db.entities.User;

public class OperationsViewModel extends AndroidViewModel {

    private final AppRepository mRepository;
    private User user;

    public OperationsViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((SharpsendApp) application).getRepository();
        mRepository.getUser().observeForever(user1 -> {
            user = user1;
        });
    }

    public int getDefaultSimPosition (ArrayList<NetworkItem.NetworkImpl> networks) {

        if (user != null && user.getDefaultSim() != null) {
            for (int i = 0; i < networks.size(); i++) {
                if (networks.get(i).getDisplayname().equalsIgnoreCase(user.getDefaultSim())) {
                    return i;
                }
            }
        }
        return 0;
    }

    public void saveDefaultSim (String displayName) {
        if (user != null) {
            user.setDefaultSim(displayName);
            mRepository.saveDefaultSim(user);
        }
    }
}
