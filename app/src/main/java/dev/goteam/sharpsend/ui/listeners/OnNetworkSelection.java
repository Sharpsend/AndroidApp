package dev.goteam.sharpsend.ui.listeners;

public interface OnNetworkSelection {
    void onNetworkSelected(int slotIdx);

    void onNetworkSelectionCanceled();
}
