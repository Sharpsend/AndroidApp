package dev.goteam.sharpsend.db.entities;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    public class GT {

        List<TransferBank> transferBankList;

        public GT() {
            transferBankList = new ArrayList<>();

            // Transfer Data
            transferBankList.add(new TransferBank("eba56884", "ACB", "Access Bank Plc"));
            transferBankList.add(new TransferBank("7a0865ca", "ACDB", "Access (Diamond) Bank Plc"));
            transferBankList.add(new TransferBank("daff7261", "KSB", "Keystone Bank Limited"));
            transferBankList.add(new TransferBank("5cf626e7", "WMB", "Wema Bank Plc"));
            transferBankList.add(new TransferBank("d89fd979", "UBN", "Union Bank of Nigeria"));
            transferBankList.add(new TransferBank("682b23b7", "STB", "Sterling Bank Plc"));
            transferBankList.add(new TransferBank("af262330", "STIB", "Stanbic IBTC Bank Plc"));
            transferBankList.add(new TransferBank("86bd25bd", "FIB", "Fidelity Bank Plc"));
            transferBankList.add(new TransferBank("d8bc6ab6", "FCMB", "First City Monument Bank Plc"));
            transferBankList.add(new TransferBank("fde4df50", "POB", "Polaris Bank Limited"));
            transferBankList.add(new TransferBank("14d0e844", "ECB", "Ecobank"));
            transferBankList.add(new TransferBank("d0152ced", "UBA", "United Bank for Africa Plc"));
            transferBankList.add(new TransferBank("864c9715", "ZEB", "Zenith Bank Plc"));
            transferBankList.add(new TransferBank("71924468", "FBN", "First Bank of Nigeria Limited"));
            transferBankList.add(new TransferBank("5da366f2", "GTB", "Guaranty Trust Bank plc"));
        }

        public List<TransferBank> getTransferBankList() {
            return transferBankList;
        }
    }

    private class TransferBank {

        private String actionID;
        private String bankName;
        private String bankCode;
        private TransferBank(String actionID, String bankCode, String bankName) {
            this.actionID = actionID;
            this.bankName = bankName;
            this.bankCode = bankCode;
        }

        public String getActionID() {
            return actionID;
        }

        public String getBankName() {
            return bankName;
        }

        public String getBankCode() {
            return bankCode;
        }
    }
}
