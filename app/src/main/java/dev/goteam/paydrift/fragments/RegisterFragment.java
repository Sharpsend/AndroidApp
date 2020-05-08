package dev.goteam.paydrift.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment implements TextWatcher {

    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.usernameTextField.getEditText().addTextChangedListener(this);
        binding.confirmPinField.getEditText().addTextChangedListener(this);
        binding.pinField.getEditText().addTextChangedListener(this);

        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RegisterFragment.this).navigateUp();
            }
        });
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            if (
                    binding.usernameTextField.getEditText().getText().toString().isEmpty()
                    || binding.confirmPinField.getEditText().getText().toString().isEmpty()
                    || binding.pinField.getEditText().getText().toString().isEmpty()
            ) {
                binding.signUpButton.setEnabled(false);
            } else {
                String pin = binding.pinField.getEditText().getText().toString();
                String confirmPin = binding.confirmPinField.getEditText().getText().toString();

                binding.signUpButton.setEnabled(pin.equals(confirmPin) && (pin.length() == 4));
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
