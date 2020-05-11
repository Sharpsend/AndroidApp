package dev.goteam.sharpsend.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.goteam.sharpsend.databinding.FragmentSettingsBinding;
import dev.goteam.sharpsend.ui.activities.ChangePinActivity;
import dev.goteam.sharpsend.utils.FingerprintUtils;
import dev.goteam.sharpsend.utils.Prefs;
import dev.goteam.sharpsend.viewmodels.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.changePinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), ChangePinActivity.class));
            }
        });

        binding.fingerprintSwitch.setEnabled(FingerprintUtils.isFingerprintSupported(getContext()));
        binding.fingerprintSwitch.setChecked(Prefs.isFingerprintEnabled(requireContext()));

        binding.fingerprintSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Prefs.setFingerprintState(requireContext(), b);
                Toast.makeText(requireContext(), b ? "Fingerprint Enabled" : "Fingerprint Disabled", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
