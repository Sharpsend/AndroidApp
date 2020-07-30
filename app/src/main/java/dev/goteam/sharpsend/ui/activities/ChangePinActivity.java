package dev.goteam.sharpsend.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.ActivityChangePinBinding;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.utils.Prefs;
import dev.goteam.sharpsend.viewmodels.ModifyPinViewModel;

public class ChangePinActivity extends AppCompatActivity implements TextWatcher {

    private ActivityChangePinBinding binding;
    private ModifyPinViewModel viewModel;

    private String oldPin;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_pin);

        binding.currentPinField.getEditText().addTextChangedListener(this);
        binding.newPinField.getEditText().addTextChangedListener(this);
        viewModel = new ViewModelProvider(this).get(ModifyPinViewModel.class);
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    mUser = user;
                    oldPin = mUser.getPin();
                }
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPIN = binding.currentPinField.getEditText().getText().toString();
                String newPIN = binding.newPinField.getEditText().getText().toString();

                boolean isValid = currentPIN.equals(oldPin);
                if (isValid) {
                    Toast.makeText(ChangePinActivity.this, "Changing PIN....", Toast.LENGTH_SHORT).show();
                    mUser.setPin(newPIN);
                    viewModel.updatePin(mUser);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePinActivity.this, "PIN Changed", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, 1250);
                } else {
                    Snackbar.make(view, "Wrong PIN Supplied", Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }

    public void closeBtn(View view) {
        finish();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String currentPIN = binding.currentPinField.getEditText().getText().toString();
        String newPIN = binding.newPinField.getEditText().getText().toString();
        if (!currentPIN.isEmpty() && !newPIN.isEmpty() && currentPIN.length() == 4 && newPIN.length() == 4 && !currentPIN.equals(newPIN)) {
            binding.changeButton.setEnabled(true);
        } else {
            binding.changeButton.setEnabled(false);
        }

        if (currentPIN.equals(newPIN)) {
            Toast.makeText(this, "You cannot provide the same pin to be changed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
}
