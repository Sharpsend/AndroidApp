package dev.goteam.paydrift.listeners;

import androidx.biometric.BiometricPrompt;

public interface OnFingerprintAuthenticatedListener {

    void onSuccess();

    void onError(String error);

}
