package dev.goteam.sharpsend.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentLoginBinding;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.ui.activities.ForgotPinActivity;
import dev.goteam.sharpsend.ui.listeners.OnFingerprintAuthenticatedListener;
import dev.goteam.sharpsend.ui.activities.MainActivity;
import dev.goteam.sharpsend.utils.FingerprintUtils;
import dev.goteam.sharpsend.utils.Utils;
import dev.goteam.sharpsend.viewmodels.LoginViewModel;
import dev.goteam.sharpsend.viewmodels.LoginViewModelFactory;

public class LoginFragment extends Fragment implements TextWatcher {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;
    private OnFingerprintAuthenticatedListener mOnFingerprintAuthenticatedListener;
    private String PIN;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false
        );
        LoginViewModelFactory loginViewModelFactory = new LoginViewModelFactory(requireActivity().getApplication());
        loginViewModel = new ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel.class);

        loginViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    username = user.getUserName();
                    binding.loginHeading.setText(getResources().getString(R.string.welcome_text, username));
                    PIN = user.getPin();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fingerprintCta.setVisibility(
                (loginViewModel.isFingerPrintIsEnabled() && FingerprintUtils.isFingerprintSupported(LoginFragment.this.requireContext()))
                        ? View.VISIBLE : View.GONE
        );

        binding.forgotPinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), ForgotPinActivity.class));
            }
        });

        binding.fingerprintCta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FingerprintUtils.launchFingerprintPrompt(LoginFragment.this, mOnFingerprintAuthenticatedListener);
            }
        });

        binding.pinField.getEditText().addTextChangedListener(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.closeKeyboard(requireActivity().getCurrentFocus(), requireContext());
                String enteredPIN = binding.pinField.getEditText().getText().toString();
                if (enteredPIN.equals(PIN)) {
                    Snackbar.make(view, "Login Successful", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(requireActivity(), MainActivity.class));
                    requireActivity().finish();
                } else {
                    Snackbar.make(view, "Wrong PIN", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mOnFingerprintAuthenticatedListener = new OnFingerprintAuthenticatedListener() {
            @Override
            public void onSuccess() {
                Snackbar.make(binding.loginButton, "Fingerprint Successful", Snackbar.LENGTH_LONG).show();
                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
            }

            @Override
            public void onError(String error) {
                Snackbar.make(binding.loginButton, "Error Occurred: " + error, Snackbar.LENGTH_LONG).show();
            }
        };

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
