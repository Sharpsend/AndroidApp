package dev.goteam.paydrift.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.ui.fragments.TransferFundsFragment;
import dev.goteam.paydrift.utils.Constants;

public class OperationsActivity extends AppCompatActivity {

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
    }

    private void setUpOperatingFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;

        switch (operation_id) {
            case Constants.OPERATION_FUNDS_TRANSFER:
                fragment = new TransferFundsFragment();
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, String.valueOf(operation_id));
                fragmentTransaction.commit();
            break;
            default:
                break;
        }

    }

}
