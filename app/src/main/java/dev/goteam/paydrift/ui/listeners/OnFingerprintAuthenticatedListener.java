package dev.goteam.paydrift.ui.listeners;

public interface OnFingerprintAuthenticatedListener {

    void onSuccess();

    void onError(String error);

}
