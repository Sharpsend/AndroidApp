package dev.goteam.sharpsend.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import dev.goteam.sharpsend.SharpsendApp;
import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.databinding.FragmentRegisterBinding;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.ui.activities.MainActivity;
import dev.goteam.sharpsend.utils.Prefs;

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

        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RegisterFragment.this).navigateUp();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.usernameTextField.getEditText().getText().toString();

                Prefs.saveUser(RegisterFragment.this.getContext(), username);
                Snackbar.make(view, "User Registered", Snackbar.LENGTH_LONG).show();

                ((SharpsendApp) requireActivity().getApplication()).getRepository().saveUser(new User(username));

                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
            }
        });

        // TODO: effect it in the settings too and react to the set values

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
            if (binding.usernameTextField.getEditText().getText().toString().isEmpty()) {
                binding.signUpButton.setEnabled(false);
            } else {
                binding.signUpButton.setEnabled(true);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
