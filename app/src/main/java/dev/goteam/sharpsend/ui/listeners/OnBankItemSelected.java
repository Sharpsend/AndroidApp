package dev.goteam.sharpsend.ui.listeners;

import dev.goteam.sharpsend.db.entities.BankItem;

public interface OnBankItemSelected {

        void onBankItemSelected(BankItem.Bank bank);
}
