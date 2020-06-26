package dev.goteam.sharpsend.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.ui.activities.AuthActivity;
import dev.goteam.sharpsend.ui.activities.MainActivity;
import dev.goteam.sharpsend.utils.Prefs;

public class SplashScreenFragment extends Fragment {

    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // check if this is the first time the user opens the app - preferences would be used.
                if (isFirstTime()) {
                    NavHostFragment.findNavController(
                            SplashScreenFragment.this).navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToOnboardingFragment()
                    );
                } else if(isPinEnabled()) {
                    // add logic to navigate to the login screen
                    NavHostFragment.findNavController(
                            SplashScreenFragment.this
                    ).navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment());
                } else {
                    startActivity(new Intent(requireActivity(), MainActivity.class));
                    requireActivity().finish();
                }
            }
        }, 2500);
    }

    private boolean isFirstTime() {
        return !Prefs.isAuthenticated(requireContext());
    }

    private boolean isPinEnabled() { return Prefs.isPinEnabled(requireContext()); }

}
