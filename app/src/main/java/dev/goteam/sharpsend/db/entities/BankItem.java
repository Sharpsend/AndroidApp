package dev.goteam.sharpsend.db.entities;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.utils.Constants;

public class BankItem {

    public class GT implements Bank {

        private List<TransferBankAction> transferBankActionList;
        private boolean selected;

        public GT() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new TransferBankAction("eba56884", "ACB", "Access Bank Plc"));
            transferBankActionList.add(new TransferBankAction("7a0865ca", "ACDB", "Access (Diamond) Bank Plc"));
            transferBankActionList.add(new TransferBankAction("daff7261", "KSB", "Keystone Bank Limited"));
            transferBankActionList.add(new TransferBankAction("5cf626e7", "WMB", "Wema Bank Plc"));
            transferBankActionList.add(new TransferBankAction("d89fd979", "UBN", "Union Bank of Nigeria"));
            transferBankActionList.add(new TransferBankAction("682b23b7", "STB", "Sterling Bank Plc"));
            transferBankActionList.add(new TransferBankAction("af262330", "STIB", "Stanbic IBTC Bank Plc"));
            transferBankActionList.add(new TransferBankAction("86bd25bd", "FIB", "Fidelity Bank Plc"));
            transferBankActionList.add(new TransferBankAction("d8bc6ab6", "FCMB", "First City Monument Bank Plc"));
            transferBankActionList.add(new TransferBankAction("fde4df50", "POB", "Polaris Bank Limited"));
            transferBankActionList.add(new TransferBankAction("14d0e844", "ECB", "Ecobank"));
            transferBankActionList.add(new TransferBankAction("d0152ced", "UBA", "United Bank for Africa Plc"));
            transferBankActionList.add(new TransferBankAction("864c9715", "ZEB", "Zenith Bank Plc"));
            transferBankActionList.add(new TransferBankAction("71924468", "FBN", "First Bank of Nigeria Limited"));
            transferBankActionList.add(new TransferBankAction("5da366f2", "GTB", "Guaranty Trust Bank plc"));
        }

        @Override
        public RechargeAction getSelfRechargeAction() {
            return new RechargeAction("4e0c3328", "SELF", Constants.MOBILE_NUMBER_SELF);
        }

        @Override
        public RechargeAction getOthersRechargeAction() {
            return new RechargeAction("60e06f98", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY);
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
        public List<TransferBankAction> getTransferBankActionList() {
            return transferBankActionList;
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

        private List<TransferBankAction> transferBankActionList;
        private boolean selected;

        public ST() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new TransferBankAction("eba56884", "ACB", "Access Bank Plc"));
            transferBankActionList.add(new TransferBankAction("7a0865ca", "ACDB", "Access (Diamond) Bank Plc"));
            transferBankActionList.add(new TransferBankAction("daff7261", "KSB", "Keystone Bank Limited"));
            transferBankActionList.add(new TransferBankAction("5cf626e7", "WMB", "Wema Bank Plc"));
            transferBankActionList.add(new TransferBankAction("d89fd979", "UBN", "Union Bank of Nigeria"));
            transferBankActionList.add(new TransferBankAction("682b23b7", "STB", "Sterling Bank Plc"));
            transferBankActionList.add(new TransferBankAction("af262330", "STIB", "Stanbic IBTC Bank Plc"));
            transferBankActionList.add(new TransferBankAction("86bd25bd", "FIB", "Fidelity Bank Plc"));
            transferBankActionList.add(new TransferBankAction("d8bc6ab6", "FCMB", "First City Monument Bank Plc"));
            transferBankActionList.add(new TransferBankAction("fde4df50", "POB", "Polaris Bank Limited"));
            transferBankActionList.add(new TransferBankAction("14d0e844", "ECB", "Ecobank"));
            transferBankActionList.add(new TransferBankAction("d0152ced", "UBA", "United Bank for Africa Plc"));
            transferBankActionList.add(new TransferBankAction("864c9715", "ZEB", "Zenith Bank Plc"));
            transferBankActionList.add(new TransferBankAction("71924468", "FBN", "First Bank of Nigeria Limited"));
            transferBankActionList.add(new TransferBankAction("5da366f2", "GTB", "Guaranty Trust Bank plc"));
        }

        @Override
        public RechargeAction getSelfRechargeAction() {
            return new RechargeAction("4e0c3328", "SELF", Constants.MOBILE_NUMBER_SELF);
        }

        @Override
        public RechargeAction getOthersRechargeAction() {
            return new RechargeAction("60e06f98", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY);
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
        public List<TransferBankAction> getTransferBankActionList() {
            return transferBankActionList;
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

        private List<TransferBankAction> transferBankActionList;
        private boolean selected;

        public KT() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new TransferBankAction("eba56884", "ACB", "Access Bank Plc"));
            transferBankActionList.add(new TransferBankAction("7a0865ca", "ACDB", "Access (Diamond) Bank Plc"));
            transferBankActionList.add(new TransferBankAction("daff7261", "KSB", "Keystone Bank Limited"));
            transferBankActionList.add(new TransferBankAction("5cf626e7", "WMB", "Wema Bank Plc"));
            transferBankActionList.add(new TransferBankAction("d89fd979", "UBN", "Union Bank of Nigeria"));
            transferBankActionList.add(new TransferBankAction("682b23b7", "STB", "Sterling Bank Plc"));
            transferBankActionList.add(new TransferBankAction("af262330", "STIB", "Stanbic IBTC Bank Plc"));
            transferBankActionList.add(new TransferBankAction("86bd25bd", "FIB", "Fidelity Bank Plc"));
            transferBankActionList.add(new TransferBankAction("d8bc6ab6", "FCMB", "First City Monument Bank Plc"));
            transferBankActionList.add(new TransferBankAction("fde4df50", "POB", "Polaris Bank Limited"));
            transferBankActionList.add(new TransferBankAction("14d0e844", "ECB", "Ecobank"));
            transferBankActionList.add(new TransferBankAction("d0152ced", "UBA", "United Bank for Africa Plc"));
            transferBankActionList.add(new TransferBankAction("864c9715", "ZEB", "Zenith Bank Plc"));
            transferBankActionList.add(new TransferBankAction("71924468", "FBN", "First Bank of Nigeria Limited"));
            transferBankActionList.add(new TransferBankAction("5da366f2", "GTB", "Guaranty Trust Bank plc"));
        }

        @Override
        public RechargeAction getSelfRechargeAction() {
            return new RechargeAction("4e0c3328", "SELF", Constants.MOBILE_NUMBER_SELF);
        }

        @Override
        public RechargeAction getOthersRechargeAction() {
            return new RechargeAction("60e06f98", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY);
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
        public List<TransferBankAction> getTransferBankActionList() {
            return transferBankActionList;
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

        private List<TransferBankAction> transferBankActionList;
        private boolean selected;

        public PT() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new TransferBankAction("eba56884", "ACB", "Access Bank Plc"));
            transferBankActionList.add(new TransferBankAction("7a0865ca", "ACDB", "Access (Diamond) Bank Plc"));
            transferBankActionList.add(new TransferBankAction("daff7261", "KSB", "Keystone Bank Limited"));
            transferBankActionList.add(new TransferBankAction("5cf626e7", "WMB", "Wema Bank Plc"));
            transferBankActionList.add(new TransferBankAction("d89fd979", "UBN", "Union Bank of Nigeria"));
            transferBankActionList.add(new TransferBankAction("682b23b7", "STB", "Sterling Bank Plc"));
            transferBankActionList.add(new TransferBankAction("af262330", "STIB", "Stanbic IBTC Bank Plc"));
            transferBankActionList.add(new TransferBankAction("86bd25bd", "FIB", "Fidelity Bank Plc"));
            transferBankActionList.add(new TransferBankAction("d8bc6ab6", "FCMB", "First City Monument Bank Plc"));
            transferBankActionList.add(new TransferBankAction("fde4df50", "POB", "Polaris Bank Limited"));
            transferBankActionList.add(new TransferBankAction("14d0e844", "ECB", "Ecobank"));
            transferBankActionList.add(new TransferBankAction("d0152ced", "UBA", "United Bank for Africa Plc"));
            transferBankActionList.add(new TransferBankAction("864c9715", "ZEB", "Zenith Bank Plc"));
            transferBankActionList.add(new TransferBankAction("71924468", "FBN", "First Bank of Nigeria Limited"));
            transferBankActionList.add(new TransferBankAction("5da366f2", "GTB", "Guaranty Trust Bank plc"));
        }

        @Override
        public RechargeAction getSelfRechargeAction() {
            return new RechargeAction("4e0c3328", "SELF", Constants.MOBILE_NUMBER_SELF);
        }

        @Override
        public RechargeAction getOthersRechargeAction() {
            return new RechargeAction("60e06f98", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY);
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
        public List<TransferBankAction> getTransferBankActionList() {
            return transferBankActionList;
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
        RechargeAction getSelfRechargeAction();
        RechargeAction getOthersRechargeAction();
        List<TransferBankAction> getTransferBankActionList();

    }

    public class TransferBankAction {

        private String actionID;
        private String name;
        private String code;
        private boolean isSelected;
        private TransferBankAction(String actionID, String code, String name) {
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

    public class RechargeAction {

        private String actionID;
        private String name;
        private String code;
        private RechargeAction(String actionID, String code, String name) {
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
    }
}
