package dev.goteam.sharpsend.ui.listeners;

public interface OnFingerprintAuthenticatedListener {

    void onSuccess();

    void onError(String error);

}
