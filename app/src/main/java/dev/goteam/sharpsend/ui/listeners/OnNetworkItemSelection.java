package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.MobileItem;
import dev.goteam.sharpsend.db.entities.NetworkItem;

public interface OnNetworkItemSelection {

    void onNetworkSelected(NetworkItem.NetworkImpl network);

}
