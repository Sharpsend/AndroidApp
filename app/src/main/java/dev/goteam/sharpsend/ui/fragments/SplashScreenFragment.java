package dev.goteam.sharpsend.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hover.sdk.actions.HoverAction;
import com.hover.sdk.api.Hover;

import java.util.ArrayList;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.ui.activities.AuthActivity;
import dev.goteam.sharpsend.ui.activities.MainActivity;
import dev.goteam.sharpsend.utils.Prefs;

import static android.content.ContentValues.TAG;

public class SplashScreenFragment extends Fragment implements Hover.DownloadListener {

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
                Log.i(TAG, "run: " + isFirstTime() + isPinEnabled());
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

        // Initialize Hover
        Hover.initialize(getActivity().getApplicationContext(), this);
    }

    private boolean isFirstTime() {
        return !Prefs.isAuthenticated(requireContext());
    }

    private boolean isPinEnabled() { return Prefs.isPinEnabled(requireContext()); }

    @Override
    public void onError(String s) {
        Log.e(TAG, "Error: " + s);
    }

    @Override
    public void onSuccess(ArrayList<HoverAction> arrayList) {
        //Toast.makeText(this, "Successfully downloaded " + arrayList.size() + " actions", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Successfully downloaded " + arrayList.size() + " actions");
    }
}
