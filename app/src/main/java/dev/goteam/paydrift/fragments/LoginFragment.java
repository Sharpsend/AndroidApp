package dev.goteam.paydrift.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.databinding.FragmentLoginBinding;
import dev.goteam.paydrift.viewmodels.LoginViewModel;
import dev.goteam.paydrift.viewmodels.LoginViewModelFactory;

public class LoginFragment extends Fragment implements TextWatcher {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false
        );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginViewModelFactory loginViewModelFactory = new LoginViewModelFactory(requireActivity().getApplication());
        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel.class);
        binding.loginHeading.setText(getResources().getString(R.string.welcome_text, loginViewModel.getUsername()));

        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().finish();
            }
        });

        binding.pinField.getEditText().addTextChangedListener(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredPIN = binding.pinField.getEditText().getText().toString();
                if (enteredPIN.equals(loginViewModel.getPin())) {
                    Snackbar.make(view, "Login Successful", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "Wrong PIN", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String PIN = binding.pinField.getEditText().getText().toString();
        binding.loginButton.setEnabled(PIN.length() == 4);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
}
