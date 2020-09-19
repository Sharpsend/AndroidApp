package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.Selectable;

public interface OnMobileSelection {
    void onMobileSelected(Selectable.Item item);

    void onMobileSelectionCanceled();
}
