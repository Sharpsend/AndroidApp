package dev.goteam.sharpsend.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hover.sdk.api.HoverConfigException;
import com.hover.sdk.api.HoverParameters;

import dev.goteam.sharpsend.AccessibilityTipsFragment;
import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.databinding.ActivityOperationsBinding;
import dev.goteam.sharpsend.db.entities.Action;
import dev.goteam.sharpsend.db.entities.NetworkItem;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.models.StartActivityModel;
import dev.goteam.sharpsend.ui.dialogs.TransactionSuccessDialog;
import dev.goteam.sharpsend.ui.fragments.operations.SelectMobileNetworkBottomSheetFragment;
import dev.goteam.sharpsend.ui.fragments.operations.TransferFundsFragment;
import dev.goteam.sharpsend.ui.fragments.operations.BuyAirtimeFragment;
import dev.goteam.sharpsend.ui.listeners.OnContactSelectionListener;
import dev.goteam.sharpsend.ui.listeners.OnNetworkSelection;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.viewmodels.OperationsViewModel;

public class OperationsActivity extends AppCompatActivity implements OnNetworkSelection, OnContactSelectionListener {
    private OperationsViewModel operationsViewModel;
    public static User user;

    private TextInputLayout selectSimField;
    private LinearLayout header;
    private Button retryBtn;
    public static TextView title;
    private boolean isStarting = true;
    private ActivityOperationsBinding binding;

    private final String TAG = getClass().getSimpleName();
    private String operation_id;
    private MutableLiveData<StartActivityModel> startActivityModel = new MutableLiveData<>();
    private final static int SELECT_PHONE_NUMBER = 15;

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
        retryBtn = findViewById(R.id.retryBtn);
        header = findViewById(R.id.header);

        try {
            operation_id = intent.getExtras().getString("operation_id");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        // You have selected a sim with HNI 0, but the action's only valid choices are ["62120","62160","62150","62130"]

        operationsViewModel.getUser().observe(this, user1 -> {
            Log.e(TAG, "OperationsViewModel: Got User");
            if (user1 != null) {
                user = user1;
                operationsViewModel.getSims(this, user);
                setUpSims();
                if (isStarting) {
                    isStarting = false;
                    setUpOperatingFragment();
                }
            }
        });

        selectSimField.getEditText().setOnClickListener(view1 -> launchSimSelection());
        selectSimField.setEndIconOnClickListener(view1 -> launchSimSelection());

        startActivityModel.observe(this, startActivityModel1 -> {
            if (startActivityModel1 != null) {
                startActivityForResult(startActivityModel1.getIntent(), startActivityModel1.getRequestCode());
            }
        });
    }

    private void setUpSims() {
        if (operationsViewModel.getNetworks() != null) {
            NetworkItem.NetworkImpl selectedNetwork = operationsViewModel.getNetworkFromSlot(user.getSlotIdx());
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

                header.setVisibility(View.GONE);
                fragment = new AccessibilityTipsFragment();
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "AccessibilityTipsFragment");
                fragmentTransaction.commit();
                break;
            case Constants.BUY_AIRTIME:

                fragment = new BuyAirtimeFragment(this);
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "buyAirtimeFragment");
                fragmentTransaction.commit();
                break;
            case Constants.CHECK_AIRTIME:

                //checkAirtime();
                checkAirtime();
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

        Intent intent = operationsViewModel.getCallIntent(operationsViewModel.getNetworks().get(user.getSlotIdx()).getCheckBalanceCode(), user.getSlotIdx());

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
        }

        retryBtn.setOnClickListener(view -> newCheckAirtime());
    }

    private void checkAirtime() {
        title.setText("Check Airtime");

        Action action = operationsViewModel.getNetworks().get(user.getSlotIdx()).getCheckBalanceAction();

        if (operationsViewModel.getNetworks() != null && operationsViewModel.getNetworks().size() != 0) {
            try {
                Intent i = new HoverParameters.Builder(this)
                        .request(action.getActionID()) // Add your action ID here
                        .finalMsgDisplayTime(0)
                        .buildIntent();
                getStartActivityModel()
                        .postValue(new StartActivityModel(i, Constants.OPERATIONS_CODE));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onClick: ERROR");
            }
        } else {
            Toast.makeText(this, "Accept permissions for best User Experience", Toast.LENGTH_SHORT).show();
        }

        retryBtn.setOnClickListener(view -> newCheckAirtime());
    }

    public void launchSimSelection() {
        SelectMobileNetworkBottomSheetFragment selectMobileNetworkBottomSheetFragment
                = new SelectMobileNetworkBottomSheetFragment(this, operationsViewModel.getNetworks()
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

        }
        else if (requestCode == Constants.OPERATIONS_CODE && resultCode == Activity.RESULT_CANCELED) {
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
        else if (requestCode == SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);

            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                // Do something with the phone number
                this.onContactSelected(number);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user != null)
        ((SharpsendApp) getApplication()).getAppExecutors().diskIO().execute(() -> operationsViewModel.getSims(this, user));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.CALL_PERMISSION_REQUEST:
                Log.i(TAG, "onRequestPermissionsResult: " + grantResults);
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    operationsViewModel.getSims(this, user);
                }  else {
                    // We need to call Toast.makeText() (and most other functions dealing with the UI) from within the main thread.
                    this.runOnUiThread(() -> Toast.makeText(OperationsActivity.this, "This feature is unavailable because the feature requires a permission that have been denied.", Toast.LENGTH_LONG).show());

                    finish();
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    public void closeBtn(View view) {
        finish();
    }

    @Override
    public void onNetworkSelected(int slotIdx) {
        if (operationsViewModel.getNetworks() != null) {
            NetworkItem.NetworkImpl selectedNetwork = operationsViewModel.getNetworkFromSlot(slotIdx);

            selectSimField.getEditText().setText(selectedNetwork.getDisplayName());
            operationsViewModel.saveUserSim(user, selectedNetwork.getSlotIdx());
        }
    }

    @Override
    public void onNetworkSelectionCanceled() { }

    @Override
    public void openContacts() {
        Intent selectContactIntent = new Intent(Intent.ACTION_PICK);
        selectContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(selectContactIntent, SELECT_PHONE_NUMBER);
    }

    @Override
    public void onContactSelected(String number) {
        number = number.trim();
        if (number.startsWith("+234")) {
            number = number.replace("+234", "0");
        }
        Log.d(TAG, number + " from ops");
        BuyAirtimeFragment fragment = (BuyAirtimeFragment) getSupportFragmentManager().findFragmentByTag("buyAirtimeFragment");
        if (fragment != null) {
            Log.d(TAG, "fragment found!");
            fragment.setThirdPartyMobileNumber(number);
        }

    }
}
