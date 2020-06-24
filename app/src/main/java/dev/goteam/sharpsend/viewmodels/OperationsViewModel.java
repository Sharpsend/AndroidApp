package dev.goteam.sharpsend.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.lang.UScript;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hover.sdk.api.Hover;
import com.hover.sdk.sims.SimInfo;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.AppRepository;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.ui.activities.MainActivity;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;
import dev.goteam.sharpsend.utils.Constants;

public class OperationsViewModel extends AndroidViewModel {

    private final String TAG = getClass().getSimpleName();
    private final AppRepository mRepository;
    private LiveData<User> user;
    private User user2;

    public OperationsViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((SharpsendApp) application).getRepository();
        user = mRepository.getUser();
        mRepository.getUser().observeForever(user1 -> {
            Log.e(TAG, "OperationsViewModel: Got User");
            user2 = user1;
        });
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void saveUserSim (int slotIdx) {
        if (user2 != null) {
            user2.setSlotIdx(slotIdx);
            mRepository.saveDefaultSim(user2);
        }
    }

    public ArrayList<NetworkItem.NetworkImpl> getSims(Context context, User user) {
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, Constants.SIMINFO_REQUEST);

            // Don't return, it may be automatically accepted in recent Android Versions
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, Constants.CALL_REQUEST);
                Log.e(TAG, "setupSim: 2");
                return null;
            }
        }

        List<SimInfo> simInfos = Hover.getPresentSims(context);

        if (simInfos != null) {

            ArrayList<NetworkItem.NetworkImpl> networks = new NetworkItem().getNetworksFromSimInfos(simInfos);

            if (networks != null && networks.size() > 0) {
                networks.get(user.getSlotIdx()).setSelected(true);

                Log.i(TAG, "onNetworkSelected: " + simInfos.get(0).getNetworkOperator() + "." + simInfos.get(0).getNetworkOperatorName());
            }

            return networks;
        } else {
            Toast.makeText(context, "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
        }

        return null;
    }
}
