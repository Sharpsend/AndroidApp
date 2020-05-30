package dev.goteam.sharpsend.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.db.entities.StartActivityModel;
import dev.goteam.sharpsend.ui.dialogs.TransactionSuccessDialog;
import dev.goteam.sharpsend.ui.fragments.operations.SelectMobileNetworkBottomSheetFragment;
import dev.goteam.sharpsend.ui.fragments.operations.TransferFundsFragment;
import dev.goteam.sharpsend.ui.fragments.operations.BuyAirtimeFragment;
import dev.goteam.sharpsend.ui.listeners.OnNetworkSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.utils.NetworkUtils;

public class OperationsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private MutableLiveData<StartActivityModel> startActivityModel = new MutableLiveData<>();
    public ArrayList<NetworkItem.Network> networks;

    String operation_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Intent intent = getIntent();

        try {
            operation_id = intent.getExtras().getString("operation_id");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        // Request Hover permission
        Intent i = new Intent(getApplicationContext(), PermissionActivity.class);
        startActivityForResult(i, 0);

        //setupSim();
        setUpOperatingFragment();

        startActivityModel.observe(this, startActivityModel1 -> {
            if (startActivityModel1 != null) {
                startActivityForResult(startActivityModel1.getIntent(), startActivityModel1.getRequestCode());
            }
        });
    }



    private void setupSim() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        List<SimInfo> simInfos = Hover.getPresentSims(OperationsActivity.this);

        if (simInfos != null) {

            networks = new ArrayList<>();
            for (SimInfo siminfo : simInfos) {
                NetworkItem.Network network = new NetworkItem.Network(
                        new StringBuilder()
                                .append(siminfo.getOperatorName())
                                .append(" (SIM ")
                                .append(siminfo.slotIdx + 1)
                                .append(")").toString(),
                        siminfo.getOperatorName(),
                        NetworkUtils.getSimId(siminfo.getOperatorName())
                );
                network.setImageRes(NetworkUtils.getSimRes(network.getId()));
                networks.add(network);
            }

            Log.i(TAG, "onNetworkSelected: " + simInfos.get(0).getNetworkOperator() + "." + simInfos.get(0).getNetworkOperatorName());
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

                setupSim();
                Intent i = new HoverParameters.Builder(OperationsActivity.this)
                        //.request("d8774688")
                        .request("d867290d")
                        .finalMsgDisplayTime(0)
                        //.setEnvironment(HoverParameters.DEBUG_ENV)
                        //.setSim(networks.get(0).getOperatorName())
                        .buildIntent();
                getStartActivityModel()
                        .postValue(new StartActivityModel(i, Constants.OPERATIONS_CODE));
                //checkAirtime();
                break;
            default:
                break;
        }
    }


    public MutableLiveData<StartActivityModel> getStartActivityModel() {
        return startActivityModel;
    }

    private void checkAirtime() {

        ArrayList<NetworkItem.Network> networks = new ArrayList<>();
        networks.add(new NetworkItem.Network("MTN", "mtn", R.drawable.mtn));
        networks.add(new NetworkItem.Network("GLO", "glo", R.drawable.glo));
        networks.add(new NetworkItem.Network("AIRTEL", "airtel", R.drawable.airtel));
        networks.add(new NetworkItem.Network("9MOBILE", "9mobile", R.drawable.mobile_9));

        SelectMobileNetworkBottomSheetFragment selectMobileNetworkBottomSheetFragment
                = new SelectMobileNetworkBottomSheetFragment(
                new OnNetworkSelection() {
                    @Override
                    public void onNetworkSelected(NetworkItem.Network network) {
                        Toast.makeText(getApplicationContext(), "Network selected: " + network.getName(), Toast.LENGTH_SHORT).show();

                       /* Intent j = new HoverParameters.Builder(requireActivity())
                                .request(senderBank.getOthersRechargeAction().getActionID()) // Add your action ID here
                                .extra("Amount", binding.amountField.getEditText().getText().toString())
                                .extra("PhoneNumber", binding.phoneNumberField.getEditText().getText().toString())
                                .finalMsgDisplayTime(0)
                                .buildIntent();
                        ((OperationsActivity) requireActivity()).getStartActivityModel()
                                .postValue(new StartActivityModel(j, Constants.OPERATIONS_CODE));*/
                        /*try {
                            Hover.requestActionChoice(new String[] {"d867290d", "6fe4063b", "7a47c2fd"}, new Hover.ActionChoiceListener() {
                                @Override
                                public void onActionChosen(String actionID) {
                                    Intent i = new HoverParameters.Builder(OperationsActivity.this)
                                            .request(actionID)
                                            .finalMsgDisplayTime(0)
                                            .setEnvironment(HoverParameters.DEBUG_ENV)
                                            .buildIntent();
                                    getStartActivityModel()
                                            .postValue(new StartActivityModel(i, Constants.OPERATIONS_CODE));
                                }

                                @Override
                                public void onCanceled() {
                                    Toast.makeText(getApplicationContext(), "You must choose a SIM card", Toast.LENGTH_SHORT).show();
                                }
                            }, OperationsActivity.this);
                        } catch(HoverConfigException e) {
                            e.printStackTrace();
                            Toast.makeText(OperationsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }*/

                    }
                    @Override
                    public void onNetworkSelectionCanceled() {

                    }
                }, networks
        );
        selectMobileNetworkBottomSheetFragment.show(getSupportFragmentManager(), "networkSelection");
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "onActivityResult: " + requestCode + "." + resultCode + "." + data);

        if (requestCode == Constants.OPERATIONS_CODE && resultCode == Activity.RESULT_OK) {
            String[] sessionTextArr = data.getStringArrayExtra("session_messages");
            String uuid = data.getStringExtra("uuid");

            assert sessionTextArr != null;
            String lastMessage = sessionTextArr[sessionTextArr.length - 1];

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            TransactionSuccessDialog newFragment = new TransactionSuccessDialog(lastMessage);

            fragmentTransaction.replace(R.id.operations_fragment_container, newFragment, "transactionSuccessDialog");
            fragmentTransaction.commitAllowingStateLoss();

        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            try {
                Toast.makeText(this, "Error: " + data.getStringExtra("error"), Toast.LENGTH_LONG).show();
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
        setupSim();
    }
}
