package dev.goteam.sharpsend.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.lang.UScript;
import android.net.Uri;
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
    private ArrayList<NetworkItem.NetworkImpl> networks;
    private LiveData<User> user;

    private final static String[] simSlotName = {
            "extra_asus_dial_use_dualsim",
            "com.android.phone.extra.slot",
            "slot",
            "simslot",
            "sim_slot",
            "subscription",
            "Subscription",
            "phone",
            "com.android.phone.DialingMode",
            "simSlot",
            "slot_id",
            "simId",
            "simnum",
            "phone_type",
            "slotId",
            "slotIdx"
    };

    public OperationsViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((SharpsendApp) application).getRepository();
        user = mRepository.getUser();
    }

    public Intent getCallIntent (String code, int slotIdx) {
        if (networks != null) {

            Intent callIntent = new Intent(Intent.ACTION_CALL).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(code)));

            callIntent.putExtra("com.android.phone.force.slot", true);
            callIntent.putExtra("Cdma_Supp", true);

            Log.i(TAG, "newCheckAirtime: Dialing Code: " + code);
            //Add all slots here, according to device.. (different device require different key so put all together)
            for (String s : simSlotName)
                callIntent.putExtra(s, slotIdx); //0 or 1 according to sim.......

            return callIntent;
        } else {
            return null;
        }
    }

    public LiveData<User> getUser() {
        return user;
    }

    public ArrayList<NetworkItem.NetworkImpl> getNetworks() {
        return networks;
    }

    public void saveUserSim (User user, int slotIdx) {
        if (user != null) {
            user.setSlotIdx(slotIdx);
            mRepository.saveDefaultSim(user);
        }
    }

    public void getSims(Context context, User user) {
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, Constants.SIMINFO_REQUEST);
            // Don't return, it may be automatically accepted in recent Android Versions
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, Constants.CALL_REQUEST);
                Log.e(TAG, "setupSim: 2");
                return;
            }
        }

        List<SimInfo> simInfos = Hover.getPresentSims(context);

        if (simInfos != null) {

            ArrayList<NetworkItem.NetworkImpl> networks = new NetworkItem().getNetworksFromSimInfos(simInfos);

            if (networks != null && networks.size() > 0) {
                networks.get(user.getSlotIdx()).setSelected(true);

                Log.i(TAG, "onNetworkSelected: " + simInfos.get(0).getNetworkOperator() + "." + simInfos.get(0).getNetworkOperatorName());
            }

            this.networks = networks.size() > 0 ? networks : null;
        } else {
            Toast.makeText(context, "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
        }
    }

    public NetworkItem.NetworkImpl getNetworkFromSlot(int slotIdx) {
        for (NetworkItem.NetworkImpl network : networks) {
            if (network.getSlotIdx() == slotIdx)
                return network;
        }
        return null;
    }
}
