package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.MobileItem;

public interface OnMobileSelection {
    void onMobileSelected(MobileItem.Mobile mobile);

    void onMobileSelectionCanceled();
}
