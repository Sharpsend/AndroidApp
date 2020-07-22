package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.Action;

public interface OnRecipientBankSelection {
    void onRecipientBankSelected(Action transferBankAction);
    void onSelectionCanceled();
}
