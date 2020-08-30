package dev.goteam.sharpsend.db.entities;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.utils.Constants;

public class BankItem {

    public class GT implements Bank {

        private String name = "Guaranty Trust Bank plc";
        private final ArrayList<PayBillItem> payBillItems;
        private List<Action> transferBankActionList;
        private boolean selected;

        public GT() {
            transferBankActionList = new ArrayList<>();
            payBillItems = new ArrayList<>();
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

            payBillItems.add(new PayBillItem("Data Services", "dataSvc"));
            payBillItems.add(new PayBillItem("DSTV Subscription", "dataSvc"));
            payBillItems.add(new PayBillItem("GOTV Subscription", "dataSvc"));
            payBillItems.add(new PayBillItem("Ikeja Electric", "dataSvc"));
            payBillItems.add(new PayBillItem("Ibadan Electric - IBIDEC", "dataSvc"));
            payBillItems.add(new PayBillItem("WAEC Result Checker PIN", "dataSvc"));
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
        public String getTransferBankCode(String amount, String nuban) {
            return String.format("*737*2*%s*%s#", amount, nuban);
        }

        @Override
        public String getTransferSameBankCode(String amount, String nuban) {
            return String.format("*737*1*%s*%s#", amount, nuban);
        }

        @Override
        public ArrayList<Selectable.Item> getTransferItems() {
            ArrayList<Selectable.Item> items = new ArrayList<>();
            items.add(new TransferItem(name, Constants.TRANSFER_SAME_BANK));
            items.add(new TransferItem("Other Banks", Constants.TRANSFER_OTHER_BANK));
            return items;
        }

        @Override
        public String getName() {
            return name;
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
        public List<PayBillItem> getPayBillItems() {
            return payBillItems;
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

        private final ArrayList<PayBillItem> payBillItems;
        private List<Action> transferBankActionList;
        private boolean selected;

        public ZEB() {
            payBillItems = new ArrayList<>();
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
        public String getTransferBankCode(String amount, String nuban) {
            return String.format("*966*%s*%s#", amount, nuban);
        }

        @Override
        public String getTransferSameBankCode(String amount, String nuban) {
            return null;
        }

        @Override
        public ArrayList<Selectable.Item> getTransferItems() {
            return null;
        }

        @Override
        public List<Action> getTransferBankActionList() {
            return transferBankActionList;
        }

        @Override
        public List<PayBillItem> getPayBillItems() {
            return payBillItems;
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

        private final String name = "Stanbic IBTC Bank Plc";
        private final ArrayList<PayBillItem> payBillItems;
        private List<Action> transferBankActionList;
        private boolean selected;

        public STIB() {
            payBillItems = new ArrayList<>();
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
            return name;
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public String getTransferBankCode(String amount, String nuban) {
            return String.format("*909*11*%s*%s#", amount, nuban);
        }

        @Override
        public String getTransferSameBankCode(String amount, String nuban) {
            return String.format("*909*22*%s*%s#", amount, nuban);
        }

        @Override
        public ArrayList<Selectable.Item> getTransferItems() {
            ArrayList<Selectable.Item> items = new ArrayList<>();
            items.add(new TransferItem(name, Constants.TRANSFER_SAME_BANK));
            items.add(new TransferItem("Other Banks", Constants.TRANSFER_OTHER_BANK));
            return items;
        }

        @Override
        public List<Action> getTransferBankActionList() {
            return transferBankActionList;
        }

        @Override
        public List<PayBillItem> getPayBillItems() {
            return payBillItems;
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

    public class FCMB implements Bank {

        private final ArrayList<PayBillItem> payBillItems;
        private List<Action> transferBankActionList;
        private boolean selected;

        public FCMB() {
            payBillItems = new ArrayList<>();
            transferBankActionList = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new Action("743c6d36", "ASL", " Aso Savings and Loans", Constants.Bank));
            transferBankActionList.add(new Action("a1206020", "ECB", "EcoBank", Constants.Bank));
            transferBankActionList.add(new Action("9140dd04", "FCMB", "First City Monument Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("c7a72aec", "FMB", "Fortis Item", Constants.Bank));
            transferBankActionList.add(new Action("a91682f8", "MIMO", "MIMO (Mkudi)", Constants.Bank));
            transferBankActionList.add(new Action("c1a1ecc5", "TAJB", "TAJ Bank", Constants.Bank));
        }

        @Override
        public Action getSelfRechargeAction() {
            return new Action("ffaa59f1", "SELF", Constants.MOBILE_NUMBER_SELF, Constants.Recharge);
        }

        @Override
        public Action getOthersRechargeAction() {
            return new Action("46c45bbf", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY,  Constants.Recharge);
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
            return "First City Monument Bank Plc";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public String getTransferBankCode(String amount, String nuban) {
            return String.format("*329*%s*%s#", amount, nuban);
        }

        @Override
        public String getTransferSameBankCode(String amount, String nuban) {
            return null;
        }

        @Override
        public ArrayList<Selectable.Item> getTransferItems() {
            return null;
        }

        @Override
        public List<Action> getTransferBankActionList() {
            return transferBankActionList;
        }

        @Override
        public List<PayBillItem> getPayBillItems() {
            return payBillItems;
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

    public class UBA implements Bank {

        private final String name = "United Bank for Africa Plc";
        private final ArrayList<PayBillItem> payBillItems;
        private List<Action> transferBankActionList;
        private boolean selected;

        public UBA() {
            transferBankActionList = new ArrayList<>();
            payBillItems = new ArrayList<>();
            selected = false;

            // Transfer Data
            transferBankActionList.add(new Action("2613379d", "ACB", "Access Bank Plc", Constants.Bank));
            transferBankActionList.add(new Action("bc72668c", "FBN", "First Bank of Nigeria Limited", Constants.Bank));
            transferBankActionList.add(new Action("04ea010f", "NMB", "NOVA Merchant Bank", Constants.Bank));
            transferBankActionList.add(new Action("621a76e8", "UBA", "United Bank for Africa Plc", Constants.Bank));
        }

        @Override
        public Action getSelfRechargeAction() {
            return new Action("3bc0b5be", "SELF", Constants.MOBILE_NUMBER_SELF, Constants.Recharge);
        }

        @Override
        public Action getOthersRechargeAction() {
            return new Action("63b2bdf6", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY,  Constants.Recharge);
        }

        @Override
        public String getSelfRechargeCode(String amount) {
            return String.format("*919*%s#", amount);
        }

        @Override
        public String getOthersRechargeCode(String amount, String pNumber) {
            return String.format("*919*%s*%s#", amount, pNumber);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public String getTransferBankCode(String amount, String nuban) {
            return String.format("*919*3*%s*%s#", nuban, amount);
        }

        @Override
        public String getTransferSameBankCode(String amount, String nuban) {
            return String.format("*919*4*%s*%s#", nuban, amount);
        }

        @Override
        public ArrayList<Selectable.Item> getTransferItems() {
            ArrayList<Selectable.Item> items = new ArrayList<>();
            items.add(new TransferItem(name, Constants.TRANSFER_SAME_BANK));
            items.add(new TransferItem("Other Banks", Constants.TRANSFER_OTHER_BANK));
            return items;
        }

        @Override
        public List<Action> getTransferBankActionList() {
            return transferBankActionList;
        }

        @Override
        public List<PayBillItem> getPayBillItems() {
            return payBillItems;
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

    public class FBN implements Bank {

        private final ArrayList<PayBillItem> payBillItems;
        private List<Action> transferBankActionList;
        private boolean selected;

        public FBN() {
            transferBankActionList = new ArrayList<>();
            payBillItems = new ArrayList<>();
            selected = false;
        }

        @Override
        public Action getSelfRechargeAction() {
            return new Action("3bc0b5be", "SELF", Constants.MOBILE_NUMBER_SELF, Constants.Recharge);
        }

        @Override
        public Action getOthersRechargeAction() {
            return new Action("63b2bdf6", "OTHERS", Constants.MOBILE_NUMBER_THIRD_PARTY,  Constants.Recharge);
        }

        @Override
        public String getSelfRechargeCode(String amount) {
            return String.format("*894*%s#", amount);
        }

        @Override
        public String getOthersRechargeCode(String amount, String pNumber) {
            return String.format("*894*%s*%s#", amount, pNumber);
        }

        @Override
        public String getName() {
            return "First Bank of Nigeria Limited";
        }

        @Override
        public int getImageRes() {
            return 0;
        }

        @Override
        public String getTransferBankCode(String amount, String nuban) {
            return String.format("*894*%s*%s#", amount, nuban);
        }

        @Override
        public String getTransferSameBankCode(String amount, String nuban) {
            return null;
        }

        @Override
        public ArrayList<Selectable.Item> getTransferItems() {
            return null;
        }

        @Override
        public List<Action> getTransferBankActionList() {
            return transferBankActionList;
        }

        @Override
        public List<PayBillItem> getPayBillItems() {
            return payBillItems;
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
        supportedBanks.add(new ZEB());
        supportedBanks.add(new STIB());
        supportedBanks.add(new FCMB());
        supportedBanks.add(new FBN());
        supportedBanks.add(new ACCESS());
        supportedBanks.add(new UBA());
        supportedBanks.add(new WEMA());
        return supportedBanks;
    }

    public class TransferItem implements Selectable.Item {

        String name;
        String id;
        boolean selected;

        public TransferItem(String name, String id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }
    }

    public interface Bank {

        String getName();
        int getImageRes();
        boolean isSelected();
        void setSelected(boolean selected);
        Action getSelfRechargeAction();
        Action getOthersRechargeAction();
        String getSelfRechargeCode(String amount);
        ArrayList<Selectable.Item> getTransferItems();
        String getOthersRechargeCode(String amount, String pNumber);
        /// Collects Amount and Account number to return a dialable string
        String getTransferBankCode(String amount, String nuban);
        String getTransferSameBankCode(String amount, String nuban);
        List<Action> getTransferBankActionList();
        List<PayBillItem> getPayBillItems();
    }
}
