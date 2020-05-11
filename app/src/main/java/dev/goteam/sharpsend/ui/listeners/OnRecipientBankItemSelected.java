package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.BankItem;

public interface OnRecipientBankItemSelected {

    void onBankItemSelected(BankItem.TransferBankAction transferBankAction);
}
