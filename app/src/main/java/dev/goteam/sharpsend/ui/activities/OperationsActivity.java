package dev.goteam.sharpsend.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hover.sdk.actions.HoverAction;
import com.hover.sdk.api.Hover;
import com.hover.sdk.permissions.PermissionActivity;

import java.util.ArrayList;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.ui.dialogs.TransactionSuccessDialog;
import dev.goteam.sharpsend.ui.fragments.BuyAirtimeFragment;
import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.ui.fragments.TransferFundsFragment;
import dev.goteam.sharpsend.utils.Constants;

import static com.android.volley.VolleyLog.TAG;

public class OperationsActivity extends AppCompatActivity implements Hover.DownloadListener {
    private final String TAG = "OperationsActivity";
    int operation_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Intent intent = getIntent();
        try {
            operation_id = intent.getExtras().getInt("operation_id", -1);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        setUpOperatingFragment();

        ((SharpsendApp) getApplication()).getAppExecutors().networkIO().execute(() -> {

            // Request Hover permission
            Intent i = new Intent(getApplicationContext(), PermissionActivity.class);
            startActivityForResult(i, 0);

            // Initialize Hover
            Hover.initialize(getApplicationContext(), OperationsActivity.this);
        });

    }

    private void setUpOperatingFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;

        switch (operation_id) {
            case Constants.OPERATION_FUNDS_TRANSFER:
                fragment = new TransferFundsFragment();
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "transferFundsFragment");
                fragmentTransaction.commit();
            break;
            case Constants.OPERATION_BUY_AIRTIME:
                fragment = new BuyAirtimeFragment();
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, "buyAirtimeFragment");
                fragmentTransaction.commit();
            default:
                break;
        }

    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "Error while attempting to download actions, see logcat for error", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Error: " + s);
    }

    @Override
    public void onSuccess(ArrayList<HoverAction> arrayList) {
        Toast.makeText(this, "Successfully downloaded " + arrayList.size() + " actions", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Successfully downloaded " + arrayList.size() + " actions");
    }

    public void launchIntent(Intent i, int code) {
        startActivityForResult(i, code);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "onActivityResult: ");

        if (requestCode == Constants.OPERATIONS_CODE && resultCode == Activity.RESULT_OK) {
            String[] sessionTextArr = data.getStringArrayExtra("session_messages");
            String uuid = data.getStringExtra("uuid");

            assert sessionTextArr != null;
            String lastMessage = sessionTextArr[sessionTextArr.length - 1];

            TransactionSuccessDialog newFragment = new TransactionSuccessDialog(lastMessage);
            newFragment.show(getSupportFragmentManager(), "transactionSuccessDialog");

        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Error: " + data.getStringExtra("error"), Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
