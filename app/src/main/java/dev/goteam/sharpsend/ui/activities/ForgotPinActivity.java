package dev.goteam.sharpsend.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.ActivityForgotPinBinding;
import dev.goteam.sharpsend.utils.Prefs;

public class ForgotPinActivity extends AppCompatActivity implements TextWatcher {
    
    private ActivityForgotPinBinding binding;

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
        
        binding.newPinFieldF.getEditText().addTextChangedListener(this);
        binding.confirmNewPinField.getEditText().addTextChangedListener(this);
        
        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = binding.newPinFieldF.getEditText().getText().toString();
                Prefs.setPIN(getApplicationContext(), pin);

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
