package dev.goteam.sharpsend.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import dev.goteam.sharpsend.databinding.FragmentSettingsBinding;
import dev.goteam.sharpsend.db.entities.User;
import dev.goteam.sharpsend.ui.activities.ChangePinActivity;
import dev.goteam.sharpsend.ui.activities.OperationsActivity;
import dev.goteam.sharpsend.ui.fragments.operations.ChangeUsernameBottomSheetFragment;
import dev.goteam.sharpsend.ui.fragments.operations.SetPinBottomSheetFragment;
import dev.goteam.sharpsend.ui.listeners.OnPinSetListener;
import dev.goteam.sharpsend.ui.listeners.OnUsernameChangedListener;
import dev.goteam.sharpsend.utils.Constants;
import dev.goteam.sharpsend.utils.FingerprintUtils;
import dev.goteam.sharpsend.utils.Prefs;
import dev.goteam.sharpsend.viewmodels.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    private User mUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding.requestPinSwitch.setChecked(Prefs.isPinEnabled(requireContext()));

        settingsViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    mUser = user;
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.changePinText.setVisibility(Prefs.isPinEnabled(requireContext()) ? View.VISIBLE : View.GONE);
        binding.changePinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), ChangePinActivity.class));
            }
        });

        binding.changeUsernameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeUsernameBottomSheetFragment changeUsernameBottomSheetFragment = new ChangeUsernameBottomSheetFragment(new OnUsernameChangedListener() {
                    @Override
                    public void onUsernameChanged(String newUsername) {
                        mUser.setUserName(newUsername);
                        settingsViewModel.updateUser(mUser);
                        Prefs.saveUser(requireContext(), newUsername);

                        Snackbar.make(view, "Username updated successfully!", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProcessCanceled() {
                    }
                });

                changeUsernameBottomSheetFragment.show(getParentFragmentManager(), "changeUsernameBottomSheetFragment");
            }
        });

        binding.fingerprintSwitch.setEnabled(FingerprintUtils.isFingerprintSupported(getContext()));
        binding.fingerprintSwitch.setChecked(Prefs.isFingerprintEnabled(requireContext()));

        binding.requestPinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("SettingsFragment", "Value: " + Prefs.isPinEnabled(requireContext()));
                if (!b) {
                    Prefs.setPinEnabledState(requireContext(), false);
                    binding.changePinText.setVisibility(Prefs.isPinEnabled(requireContext()) ? View.VISIBLE : View.GONE);
                    Toast.makeText(requireContext(), "PIN Support Disabled", Toast.LENGTH_SHORT).show();
                } else {
                    SetPinBottomSheetFragment setPinBottomSheetFragment = new SetPinBottomSheetFragment(new OnPinSetListener() {
                        @Override
                        public void onPinSet(String PIN) {

                            mUser.setPin(PIN);
                            settingsViewModel.updateUser(mUser);

                            Prefs.setPinEnabledState(requireContext(), true);
                            binding.changePinText.setVisibility(Prefs.isPinEnabled(requireContext()) ? View.VISIBLE : View.GONE);
                            Toast.makeText(requireContext(), "PIN Support Enabled", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPinSetCanceled() {
                            binding.requestPinSwitch.setChecked(false);
                            binding.changePinText.setVisibility(Prefs.isPinEnabled(requireContext()) ? View.VISIBLE : View.GONE);
                        }
                    });
                    setPinBottomSheetFragment.show(getParentFragmentManager(), "set_pin");

                }
            }
        });

        binding.fingerprintSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Prefs.setFingerprintState(requireContext(), b);
                if (!Prefs.isPinEnabled(getContext())) {
                    Toast.makeText(requireContext(), b ? "You have to enable PIN support to use fingerprint" : "Fingerprint Disabled", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(requireContext(), b ? "Fingerprint Enabled" : "Fingerprint Disabled", Toast.LENGTH_SHORT).show();
            }
        });

        binding.accessibilityTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent operationsIntent = new Intent(requireActivity(), OperationsActivity.class);
                operationsIntent.putExtra("operation_id", Constants.ACCESSIBILITY_TIPS);
                startActivity(operationsIntent);
            }
        });

        binding.faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sharpsend.co/#faqs"));
                startActivity(browserIntent);
            }
        });
    }
}
