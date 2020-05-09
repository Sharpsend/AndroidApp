package dev.goteam.paydrift.utils;

import java.util.ArrayList;

import dev.goteam.paydrift.R;
import dev.goteam.paydrift.db.entities.Bank;

public class DataSource {

    public static ArrayList<Bank> getBanks(int selectedBankIndex) {
        ArrayList<Bank> banks = new ArrayList<>();
        banks.add(new Bank(R.drawable.gtbank, "GT Bank", selectedBankIndex == banks.size()));
        banks.add(new Bank(R.drawable.ibtc, "Stanbic IBTC Bank", selectedBankIndex == banks.size()));

        return banks;
    }

    public static ArrayList<Bank> getRecipientBanks(int selectedBankIndex) {
        ArrayList<Bank> banks = new ArrayList<>();
        banks.add(new Bank(R.drawable.ibtc, "Access Bank PLC", selectedBankIndex == banks.size()));
        banks.add(new Bank(R.drawable.ibtc, "Fidelity Bank PLC", selectedBankIndex == banks.size()));
        banks.add(new Bank(R.drawable.ibtc, "First City Monument Bank Limited", selectedBankIndex == banks.size()));
        banks.add(new Bank(R.drawable.ibtc, "Guaranty Trust Bank PLC", selectedBankIndex == banks.size()));
        banks.add(new Bank(R.drawable.ibtc, "Stanbic IBTC Bank PLC", selectedBankIndex == banks.size()));
        banks.add(new Bank(R.drawable.ibtc, "Zenith Bank PLC", selectedBankIndex == banks.size()));

        return banks;
    }

}
