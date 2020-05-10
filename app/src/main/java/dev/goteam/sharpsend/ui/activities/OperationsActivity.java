package dev.goteam.sharpsend.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.ui.fragments.TransferFundsFragment;
import dev.goteam.sharpsend.utils.Constants;

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

//        switch (operation_id) {
//            case Constants.OPERATION_FUNDS_TRANSFER:
                fragment = new TransferFundsFragment();                                            // operation_id
                fragmentTransaction.replace(R.id.operations_fragment_container, fragment, String.valueOf(1));
                fragmentTransaction.commit();
//            break;
//            default:
//                break;
//        }

    }

}
