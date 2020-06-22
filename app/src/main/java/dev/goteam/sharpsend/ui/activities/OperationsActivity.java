package dev.goteam.sharpsend.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverConfigException;
import com.hover.sdk.api.HoverParameters;
import com.hover.sdk.permissions.PermissionActivity;
import com.hover.sdk.sims.SimInfo;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.AccessibilityTipsFragment;
import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.db.entities.StartActivityModel;
import dev.goteam.sharpsend.ui.dialogs.TransactionSuccessDialog;
import dev.goteam.sharpsend.ui.fragments.operations.TransferFundsFragment;
import dev.goteam.sharpsend.ui.fragments.operations.BuyAirtimeFragment;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.viewmodels.OperationsViewModel;

public class OperationsActivity extends AppCompatActivity {
    public static ArrayList<NetworkItem.NetworkImpl> networks;
    public static NetworkItem.NetworkImpl selectedDefaultSim;

    private final String TAG = getClass().getSimpleName();
    private String operation_id;
    private MutableLiveData<StartActivityModel> startActivityModel = new MutableLiveData<>();
    private OperationsViewModel operationsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Intent intent = getIntent();
        operationsViewModel = new ViewModelProvider(this).get(OperationsViewModel.class);


        try {
            operation_id = intent.getExtras().getString("operation_id");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        // Request Hover permission
        Intent i = new Intent(getApplicationContext(), PermissionActivity.class);
        startActivityForResult(i, 0);

        setUpOperatingFragment();

        startActivityModel.observe(this, startActivityModel1 -> {
            if (startActivityModel1 != null) {
                startActivityForResult(startActivityModel1.getIntent(), startActivityModel1.getRequestCode());
            }
        });
    }

    public void setupSim() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "setupSim: ");
            return;
        }

        List<SimInfo> simInfos = Hover.getPresentSims(OperationsActivity.this);

        if (simInfos != null) {

            networks = new ArrayList<>();
            networks = new NetworkItem().getNetworksFromSimInfos(simInfos);

            int simPosition = operationsViewModel.getDefaultSimPosition(networks);

            networks.get(simPosition).setSelected(true);
            selectedDefaultSim = networks.get(simPosition);
            Log.i(TAG, "onNetworkSelected: " + simInfos.get(0).getNetworkOperator() + "." + simInfos.get(0).getNetworkOperatorName());
        }


    }

    public ArrayList<NetworkItem.NetworkImpl> getNetworks() {
        return networks;
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

                // setupSim();
                /*Intent i = new HoverParameters.Builder(OperationsActivity.this)
                        //.request("d8774688")
                        .request("d867290d")
                        .finalMsgDisplayTime(0)
                        //.setEnvironment(HoverParameters.DEBUG_ENV)
                        //.setSim(networks.get(0).getOperatorName())
                        .buildIntent();
                getStartActivityModel()
                        .postValue(new StartActivityModel(i, Constants.OPERATIONS_CODE));*/
                checkAirtime();
                break;
            default:
                break;
        }
    }


    public MutableLiveData<StartActivityModel> getStartActivityModel() {
        return startActivityModel;
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
        ((SharpsendApp) getApplication()).getAppExecutors().diskIO().execute(() -> setupSim());
    }

    public void closeBtn(View view) {
        finish();
    }
}
