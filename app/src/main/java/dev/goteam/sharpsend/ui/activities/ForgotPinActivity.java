package dev.goteam.sharpsend.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.ActivityForgotPinBinding;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.utils.Prefs;
import dev.goteam.sharpsend.viewmodels.ModifyPinViewModel;

public class ForgotPinActivity extends AppCompatActivity implements TextWatcher {
    
    private ActivityForgotPinBinding binding;
    private ModifyPinViewModel viewModel;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_pin);
        
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewModel = new ViewModelProvider(this).get(ModifyPinViewModel.class);
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(ForgotPinActivity.class.getSimpleName(), user.getUserName());
                if (user != null) {
                    mUser = user;
                }
            }
        });
        
        binding.newPinFieldF.getEditText().addTextChangedListener(this);
        binding.confirmNewPinField.getEditText().addTextChangedListener(this);
        
        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPin = binding.newPinFieldF.getEditText().getText().toString();
                mUser.setPin(newPin);
                viewModel.updatePin(mUser);
                Toast.makeText(ForgotPinActivity.this, "PIN Changed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String newPin = binding.newPinFieldF.getEditText().getText().toString();
        String confirmPin = binding.confirmNewPinField.getEditText().getText().toString();
        
        if (newPin.equals(confirmPin) && newPin.length() == 4) {
            binding.changeButton.setEnabled(true);
        } else {
            binding.changeButton.setEnabled(false);
        }
        
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
}
