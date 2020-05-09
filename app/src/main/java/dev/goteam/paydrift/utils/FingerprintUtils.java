package dev.goteam.paydrift.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;

import dev.goteam.paydrift.ui.listeners.OnFingerprintAuthenticatedListener;

public class FingerprintUtils {

    public static boolean isFingerprintSupported(Context context) {
        BiometricManager biometricManager = BiometricManager.from(context);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                return true;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                return false;
        }

        return false;
    }

    public static void launchFingerprintPrompt(Fragment fragment, final OnFingerprintAuthenticatedListener mOnFingerprintAuthenticatedListener) {
        Executor executor = ContextCompat.getMainExecutor(fragment.requireContext());
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for Sharpsend")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use PIN")
                .build();
        BiometricPrompt biometricPrompt = new BiometricPrompt(fragment, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                mOnFingerprintAuthenticatedListener.onError(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                mOnFingerprintAuthenticatedListener.onSuccess();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                mOnFingerprintAuthenticatedListener.onError("");
            }
        });

        biometricPrompt.authenticate(promptInfo);
    }

}
