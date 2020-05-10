package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.BankItem;

public interface OnRecipientBankSelection {
    void onRecipientBankSelected(BankItem.TransferBankAction transferBankAction);
    void onSelectionCanceled();
}
