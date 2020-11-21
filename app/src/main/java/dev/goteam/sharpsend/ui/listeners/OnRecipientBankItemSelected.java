package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.Action;

public interface OnRecipientBankItemSelected {

    void onBankItemSelected(Action transferBankAction);
}
