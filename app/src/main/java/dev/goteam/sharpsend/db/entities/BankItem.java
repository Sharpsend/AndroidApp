package dev.goteam.sharpsend.db.entities;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.utils.Constants;

public class BankItem {

    public class GT implements Bank {

        private List<Action> transferBankActionList;
        private boolean selected;

        public GT() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new Action("eba56884", "ACB", "Access Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("7a0865ca", "ACDB", "Access (Diamond) Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("daff7261", "KSB", "Keystone Bank Limited", Constants.Bank));
            transferBankActionList.add(new Action("5cf626e7", "WMB", "Wema Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("d89fd979", "UBN", "Union Bank of Nigeria", Constants.Bank));
            transferBankActionList.add(new Action("682b23b7", "STB", "Sterling Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("af262330", "STIB", "Stanbic IBTC Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("86bd25bd", "FIB", "Fidelity Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("d8bc6ab6", "FCMB", "First City Monument Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("fde4df50", "POB", "Polaris Bank Limited", Constants.Bank));
            transferBankActionList.add(new Action("14d0e844", "ECB", "Ecobank", Constants.Bank));
            transferBankActionList.add(new Action("d0152ced", "UBA", "United Bank for Africa Plc", Constants.Bank));
            transferBankActionList.add(new Action("864c9715", "ZEB", "Zenith Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("71924468", "FBN", "First Bank of Nigeria Limited", Constants.Bank));
            transferBankActionList.add(new Action("5da366f2", "GTB", "Guaranty Trust Bank plc", Constants.Bank));
        }

        @Override
        public Action getSelfRechargeAction() {
            return new Action("4e0c3328", "SELF", Constants.MOBILE_NUMBER_SELF, Constants.Recharge);
        }

        @Override
        public Action getOthersRechargeAction() {
            return new Action("60e06f98", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY,  Constants.Recharge);
        }

        @Override
        public String getSelfRechargeCode(String amount) {
            return String.format("*737*%s#", amount);
        }

        @Override
        public String getOthersRechargeCode(String amount, String pNumber) {
            return String.format("*737*%s*%s#", amount, pNumber);
        }

        @Override
        public String getName() {
            return "Guaranty Trust Bank plc";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public List<Action> getTransferBankActionList() {
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

    public class ZEB implements Bank {

        private List<Action> transferBankActionList;
        private boolean selected;

        public ZEB() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new Action("2e87ebe5", "ZEB", "Zenith Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("2fb1fdce", "FIB", "Fidelity Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("91aae2be", "TTB", "Titan Trust Bank", Constants.Bank));
            transferBankActionList.add(new Action("7662c5db", "GTB", "Guaranty Trust Bank plc", Constants.Bank));
        }

        @Override
        public Action getSelfRechargeAction() {
            return new Action("aa192150", "SELF", Constants.MOBILE_NUMBER_SELF, Constants.Recharge);
        }

        @Override
        public Action getOthersRechargeAction() {
            return new Action("5fcf013f", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY, Constants.Recharge);
        }

        @Override
        public String getSelfRechargeCode(String amount) {
            return String.format("*966*%s#", amount);
        }

        @Override
        public String getOthersRechargeCode(String amount, String pNumber) {
            return String.format("*966*%s*%s#", amount, pNumber);
        }

        @Override
        public String getName() {
            return "Zenith Bank Plc";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public List<Action> getTransferBankActionList() {
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

    public class STIB implements Bank {

        private List<Action> transferBankActionList;
        private boolean selected;

        public STIB() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new Action("babbd284", "CTB", "City Bank", Constants.Bank));
            transferBankActionList.add(new Action("c1940fe7", "STB", "Sterling Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("fe104bba", "STIB", "Stanbic IBTC Bank Plc", Constants.Bank));
        }

        @Override
        public Action getSelfRechargeAction() {
            return new Action("a22bb973", "SELF", Constants.MOBILE_NUMBER_SELF, Constants.Recharge);
        }

        @Override
        public Action getOthersRechargeAction() {
            return null;
        }

        @Override
        public String getSelfRechargeCode(String amount) {
            return String.format("*909*%s#", amount);
        }

        @Override
        public String getOthersRechargeCode(String amount, String pNumber) {
            return null;
        }

        @Override
        public String getName() {
            return "Stanbic IBTC Bank Plc";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public List<Action> getTransferBankActionList() {
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

    /*public class KT implements Bank {

        private List<Action> transferBankActionList;
        private boolean selected;

        public KT() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new Action("eba56884", "ACB", "Access Bank Plc"));
            transferBankActionList.add(new Action("7a0865ca", "ACDB", "Access (Diamond) Bank Plc"));
            transferBankActionList.add(new Action("daff7261", "KSB", "Keystone Bank Limited"));
            transferBankActionList.add(new Action("5cf626e7", "WMB", "Wema Bank Plc"));
            transferBankActionList.add(new Action("d89fd979", "UBN", "Union Bank of Nigeria"));
            transferBankActionList.add(new Action("682b23b7", "STB", "Sterling Bank Plc"));
            transferBankActionList.add(new Action("af262330", "STIB", "Stanbic IBTC Bank Plc"));
            transferBankActionList.add(new Action("86bd25bd", "FIB", "Fidelity Bank Plc"));
            transferBankActionList.add(new Action("d8bc6ab6", "FCMB", "First City Monument Bank Plc"));
            transferBankActionList.add(new Action("fde4df50", "POB", "Polaris Bank Limited"));
            transferBankActionList.add(new Action("14d0e844", "ECB", "Ecobank"));
            transferBankActionList.add(new Action("d0152ced", "UBA", "United Bank for Africa Plc"));
            transferBankActionList.add(new Action("864c9715", "ZEB", "Zenith Bank Plc"));
            transferBankActionList.add(new Action("71924468", "FBN", "First Bank of Nigeria Limited"));
            transferBankActionList.add(new Action("5da366f2", "GTB", "Guaranty Trust Bank plc"));
        }

        @Override
        public Action getSelfRechargeAction() {
            return new Action("4e0c3328", "SELF", Constants.MOBILE_NUMBER_SELF);
        }

        @Override
        public Action getOthersRechargeAction() {
            return new Action("60e06f98", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY);
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
        public List<Action> getTransferBankActionList() {
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

        private List<Action> transferBankActionList;
        private boolean selected;

        public PT() {
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new Action("eba56884", "ACB", "Access Bank Plc"));
            transferBankActionList.add(new Action("7a0865ca", "ACDB", "Access (Diamond) Bank Plc"));
            transferBankActionList.add(new Action("daff7261", "KSB", "Keystone Bank Limited"));
            transferBankActionList.add(new Action("5cf626e7", "WMB", "Wema Bank Plc"));
            transferBankActionList.add(new Action("d89fd979", "UBN", "Union Bank of Nigeria"));
            transferBankActionList.add(new Action("682b23b7", "STB", "Sterling Bank Plc"));
            transferBankActionList.add(new Action("af262330", "STIB", "Stanbic IBTC Bank Plc"));
            transferBankActionList.add(new Action("86bd25bd", "FIB", "Fidelity Bank Plc"));
            transferBankActionList.add(new Action("d8bc6ab6", "FCMB", "First City Monument Bank Plc"));
            transferBankActionList.add(new Action("fde4df50", "POB", "Polaris Bank Limited"));
            transferBankActionList.add(new Action("14d0e844", "ECB", "Ecobank"));
            transferBankActionList.add(new Action("d0152ced", "UBA", "United Bank for Africa Plc"));
            transferBankActionList.add(new Action("864c9715", "ZEB", "Zenith Bank Plc"));
            transferBankActionList.add(new Action("71924468", "FBN", "First Bank of Nigeria Limited"));
            transferBankActionList.add(new Action("5da366f2", "GTB", "Guaranty Trust Bank plc"));
        }

        @Override
        public Action getSelfRechargeAction() {
            return new Action("4e0c3328", "SELF", Constants.MOBILE_NUMBER_SELF);
        }

        @Override
        public Action getOthersRechargeAction() {
            return new Action("60e06f98", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY);
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
        public List<Action> getTransferBankActionList() {
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
    }*/

    public ArrayList<BankItem.Bank> getSupportedBanks() {
        ArrayList<Bank> supportedBanks = new ArrayList<>();
        supportedBanks.add(new GT());
        supportedBanks.add(new ZEB());
        supportedBanks.add(new STIB());
        return supportedBanks;
    }

    public interface Bank {

        String getName();
        int getImageRes();
        boolean isSelected();
        void setSelected(boolean selected);
        Action getSelfRechargeAction();
        Action getOthersRechargeAction();
        String getSelfRechargeCode(String amount);
        String getOthersRechargeCode(String amount, String pNumber);
        List<Action> getTransferBankActionList();
    }
}
