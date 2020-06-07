package dev.goteam.sharpsend.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Iterator;

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

    public ArrayList<NetworkItem.NetworkImpl> selectDefaultSim(ArrayList<NetworkItem.NetworkImpl> networks) {

        if (user != null && user.getDefaultSim() != null) {
            /*for (Iterator<NetworkItem.NetworkImpl> it = networks.iterator(); it.hasNext(); ) {
                NetworkItem.NetworkImpl network = it.next();


            }*/

            for (int i = 0; i < networks.size(); i++) {
                if (networks.get(i).getDisplayname().equalsIgnoreCase(user.getDefaultSim())) {
                    networks.get(i).setSelected(true);
                } else {
                    networks.get(i).setSelected(false);
                }
            }
        }
        return networks;
    }

    public void saveDefaultSim (String displayName) {
        if (user != null) {
            user.setDefaultSim(displayName);
            mRepository.saveDefaultSim(user);
        }
    }
}
