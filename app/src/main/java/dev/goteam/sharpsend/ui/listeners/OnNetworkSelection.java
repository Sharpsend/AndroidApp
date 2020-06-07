package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.MobileItem;
import dev.goteam.sharpsend.db.entities.NetworkItem;

public interface OnNetworkSelection {
    void onNetworkSelected(NetworkItem.NetworkImpl network);

    void onNetworkSelectionCanceled();
}
