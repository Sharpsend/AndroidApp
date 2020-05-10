package dev.goteam.sharpsend.db.entities;

import java.util.ArrayList;
import java.util.List;

public class BankItem {

    public class GT implements Bank {

        private List<TransferBank> transferBankList;
        private boolean selected;

        public GT() {
            transferBankList = new ArrayList<>();
            selected = false;

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

        @Override
        public String getName() {
            return "Guarantee Trust Bank";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public List<TransferBank> getTransferBankList() {
            return transferBankList;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public class ST implements Bank {

        private List<TransferBank> transferBankList;
        private boolean selected;

        public ST() {
            transferBankList = new ArrayList<>();
            selected = false;

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

        @Override
        public String getName() {
            return "ST Bank";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public List<TransferBank> getTransferBankList() {
            return transferBankList;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public class KT implements Bank {

        private List<TransferBank> transferBankList;
        private boolean selected;

        public KT() {
            transferBankList = new ArrayList<>();
            selected = false;

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

        @Override
        public String getName() {
            return "KT Bank";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public List<TransferBank> getTransferBankList() {
            return transferBankList;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public class PT implements Bank {

        private List<TransferBank> transferBankList;
        private boolean selected;

        public PT() {
            transferBankList = new ArrayList<>();
            selected = false;

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

        @Override
        public String getName() {
            return "PT Bank";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public List<TransferBank> getTransferBankList() {
            return transferBankList;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public ArrayList<BankItem.Bank> getSupportedBanks() {
        ArrayList<Bank> supportedBanks = new ArrayList<>();
        supportedBanks.add(new GT());
        supportedBanks.add(new ST());
        supportedBanks.add(new KT());
        supportedBanks.add(new PT());
        return supportedBanks;
    }

    public interface Bank {

        String getName();
        int getImageRes();
        boolean isSelected();
        void setSelected(boolean selected);
        List<TransferBank> getTransferBankList();
    }

    public class TransferBank {

        private String actionID;
        private String name;
        private String code;
        private boolean isSelected;
        private TransferBank(String actionID, String code, String name) {
            isSelected = false;
            this.actionID = actionID;
            this.name = name;
            this.code = code;
        }

        public String getActionID() {
            return actionID;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
