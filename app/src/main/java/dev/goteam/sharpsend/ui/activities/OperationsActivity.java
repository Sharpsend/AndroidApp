package dev.goteam.sharpsend.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverConfigException;
import com.hover.sdk.api.HoverParameters;

import java.util.ArrayList;

import dev.goteam.sharpsend.AccessibilityTipsFragment;
import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.databinding.ActivityOperationsBinding;
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.models.StartActivityModel;
import dev.goteam.sharpsend.ui.dialogs.TransactionSuccessDialog;
import dev.goteam.sharpsend.ui.fragments.operations.SelectMobileNetworkBottomSheetFragment;
import dev.goteam.sharpsend.ui.fragments.operations.TransferFundsFragment;
import dev.goteam.sharpsend.ui.fragments.operations.BuyAirtimeFragment;
import dev.goteam.sharpsend.ui.listeners.OnNetworkSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.viewmodels.OperationsViewModel;

public class OperationsActivity extends AppCompatActivity implements OnNetworkSelection {
    public static ArrayList<NetworkItem.NetworkImpl> networks;
    public static NetworkItem.NetworkImpl selectedDefaultSim;
    private OperationsViewModel operationsViewModel;
    private User user;
    private TextInputLayout selectSimField;
    private TextView title;
    private boolean isStarting = true;
    private ActivityOperationsBinding binding;

    private final String TAG = getClass().getSimpleName();
    private String operation_id;
    private MutableLiveData<StartActivityModel> startActivityModel = new MutableLiveData<>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Intent intent = getIntent();
        binding = ActivityOperationsBinding.inflate(getLayoutInflater());
        operationsViewModel = new ViewModelProvider(this).get(OperationsViewModel.class);

        // View Binding is misbehaving, that is why i use this
        selectSimField = findViewById(R.id.select_sim_field);
        title = findViewById(R.id.title);

        try {
            operation_id = intent.getExtras().getString("operation_id");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        operationsViewModel.getUser().observe(this, user1 -> {
            Log.e(TAG, "OperationsViewModel: Got User");
            if (user1 != null) {
                user = user1;
                networks = operationsViewModel.getSims(this, user);
                setUpSims();
                if (isStarting) {
                    isStarting = false;
                    setUpOperatingFragment();
                }
            }
        });

        selectSimField.getEditText().setOnClickListener(view1 -> launchSimSelection());

        startActivityModel.observe(this, startActivityModel1 -> {
            if (startActivityModel1 != null) {
                startActivityForResult(startActivityModel1.getIntent(), startActivityModel1.getRequestCode());
            }
        });
    }

    private void setUpSims() {
        if (networks != null) {
            NetworkItem.NetworkImpl selectedNetwork = getNetworkFromSlot(user.getSlotIdx());

            selectSimField.getEditText().setText(selectedNetwork.getDisplayName());
        }
    }

    private void setUpOperatingFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;

        switch (operation_id) {
            case Constants.TRANSFER_FUNDS:

                fragment = new TransferFundsFragment();
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "transferFundsFragment");
                fragmentTransaction.commit();
                break;
            case Constants.ACCESSIBILITY_TIPS:

                fragment = new AccessibilityTipsFragment();
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "AccessibilityTipsFragment");
                fragmentTransaction.commit();
                break;
            case Constants.BUY_AIRTIME:

                fragment = new BuyAirtimeFragment();
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "buyAirtimeFragment");
                fragmentTransaction.commit();
                break;
            case Constants.CHECK_AIRTIME:

                //checkAirtime();
                newCheckAirtime();
                break;
            default:
                break;
        }
    }

    public MutableLiveData<StartActivityModel> getStartActivityModel() {
        return startActivityModel;
    }

    private void newCheckAirtime() {
        title.setText("Check Airtime");
        if (networks != null) {
            String code = networks.get(user.getSlotIdx()).getCheckBalanceCode();
            Intent callIntent = new Intent(Intent.ACTION_CALL).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(code)));

            callIntent.putExtra("com.android.phone.force.slot", true);
            callIntent.putExtra("Cdma_Supp", true);

            Log.i(TAG, "newCheckAirtime: Dialing Code: " + code);
            //Add all slots here, according to device.. (different device require different key so put all together)
            for (String s : simSlotName)
                callIntent.putExtra(s, user.getSlotIdx()); //0 or 1 according to sim.......

            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAirtime() {

        try {
            Hover.requestActionChoice(new String[]{"d867290d", "6fe4063b", "7a47c2fd", "efdc5dd1"}, new Hover.ActionChoiceListener() {
                @Override
                public void onActionChosen(String actionID) {
                    Intent i = new HoverParameters.Builder(OperationsActivity.this)
                            .request(actionID)
                            .finalMsgDisplayTime(0)
                            .buildIntent();
                    getStartActivityModel()
                            .postValue(new StartActivityModel(i, Constants.OPERATIONS_CODE));
                }

                @Override
                public void onCanceled() {
                    Toast.makeText(getApplicationContext(), "You must choose a SIM card", Toast.LENGTH_SHORT).show();
                }
            }, OperationsActivity.this);
        } catch (HoverConfigException e) {
            e.printStackTrace();
            Toast.makeText(OperationsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void launchSimSelection() {
        SelectMobileNetworkBottomSheetFragment selectMobileNetworkBottomSheetFragment
                = new SelectMobileNetworkBottomSheetFragment(this, networks
        );
        selectMobileNetworkBottomSheetFragment.show(getSupportFragmentManager(), "networkSelection");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "onActivityResult: " + requestCode + "." + resultCode + "." + data);

        try {
            Log.i(TAG, "onActivityResult: " + requestCode + "." + resultCode + "." + data.getStringExtra("error") +  data.getStringArrayExtra("session_messages").length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (requestCode == Constants.OPERATIONS_CODE && resultCode == Activity.RESULT_OK) {
            String[] sessionTextArr = data.getStringArrayExtra("session_messages");
            String uuid = data.getStringExtra("uuid");

            assert sessionTextArr != null;
            String lastMessage = sessionTextArr[sessionTextArr.length - 1];

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            TransactionSuccessDialog newFragment = new TransactionSuccessDialog(lastMessage);

            fragmentTransaction.replace(R.id.operations_fragment_container, newFragment, "transactionSuccessDialog");
            fragmentTransaction.commitAllowingStateLoss();

        } else if (requestCode == Constants.OPERATIONS_CODE && resultCode == Activity.RESULT_CANCELED) {
            try {
                Toast.makeText(this, "Error: " + data.getStringExtra("error"), Toast.LENGTH_LONG).show();
                finish();
            } catch (Exception e) {
                if (e instanceof HoverConfigException) {
                    Toast.makeText(this, "A one time Internet connection is needed for proper usage,\nThanks 🙂", Toast.LENGTH_LONG).show();
                }
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user != null)
        ((SharpsendApp) getApplication()).getAppExecutors().diskIO().execute(() -> networks = operationsViewModel.getSims(this, user));
    }

    public void closeBtn(View view) {
        finish();
    }

    @Override
    public void onNetworkSelected(int slotIdx) {
        if (networks != null) {
            NetworkItem.NetworkImpl selectedNetwork = getNetworkFromSlot(slotIdx);

            selectSimField.getEditText().setText(selectedNetwork.getDisplayName());
            operationsViewModel.saveUserSim(selectedNetwork.getSlotIdx());
        }
    }

    private NetworkItem.NetworkImpl getNetworkFromSlot(int slotIdx) {
        for (NetworkItem.NetworkImpl network : networks) {
            if (network.getSlotIdx() == slotIdx)
                return network;
        }
        return null;
    }

    @Override
    public void onNetworkSelectionCanceled() {
    }
}
