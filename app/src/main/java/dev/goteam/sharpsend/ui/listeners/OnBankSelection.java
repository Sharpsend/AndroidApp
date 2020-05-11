package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.BankItem;

public interface OnBankSelection {
    void onBankSelected(BankItem.Bank bank);
    void onSelectionCanceled();
}
