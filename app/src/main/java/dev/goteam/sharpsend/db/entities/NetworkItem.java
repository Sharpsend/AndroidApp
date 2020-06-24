package dev.goteam.sharpsend.db.entities;

import com.hover.sdk.sims.SimInfo;

import java.util.ArrayList;
import java.util.List;

import dev.goteam.sharpsend.R;
import dev.goteam.sharpsend.utils.Constants;

public class NetworkItem {

    public static class Mtn implements NetworkImpl {

        String networkOperatorCode;
        String displayName;
        String id;
        boolean selected;
        int slotIdx;
        int imageRes = R.drawable.mtn;

        public Mtn(String operatorName, int slotIdx, String simId, String networkOperatorCode) {
            this.networkOperatorCode = networkOperatorCode;
            this.slotIdx = slotIdx;
            displayName = operatorName;
            this.id = simId;
        }

        @Override
        public String getNetworkOperatorCode() {
            return networkOperatorCode;
        }

        @Override
        public Action getCheckBalanceAction() {
            return new Action("d867290d", Constants.CHECK_AIRTIME, "Check Airtime");
        }

        @Override
        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String getId() {
            return id;
        }

        public int getImage() {
            return imageRes;
        }

        @Override
        public int getSlotIdx() {
            return slotIdx;
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
    public static class Glo implements NetworkImpl {

        private int slotIdx;
        String operatorName;
        String displayname;
        String id;
        boolean selected;
        int imageRes = R.drawable.glo;

        public Glo(String operatorName, int slotIdx, String simId, String networkOperatorCode) {
            this.operatorName = operatorName;
            this.slotIdx = slotIdx;
            displayname = operatorName;
            this.id = simId;
        }

        @Override
        public String getNetworkOperatorCode() {
            return operatorName;
        }

        @Override
        public Action getCheckBalanceAction() {
            return new Action("d867290d", Constants.CHECK_AIRTIME, "Check Airtime");
        }

        @Override
        public String getDisplayName() {
            return displayname;
        }

        @Override
        public String getId() {
            return id;
        }

        public int getImage() {
            return imageRes;
        }

        @Override
        public int getSlotIdx() {
            return slotIdx;
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
    public static class Airtel implements NetworkImpl {

        private int slotIdx;
        String operatorName;
        String displayname;
        String id;
        boolean selected;
        int imageRes = R.drawable.airtel;

        public Airtel(String operatorName, int slotIdx, String simId, String networkOperatorCode) {
            this.operatorName = operatorName;
            this.slotIdx = slotIdx;
            displayname = operatorName;
            this.id = simId;
        }

        @Override
        public String getNetworkOperatorCode() {
            return operatorName;
        }

        @Override
        public Action getCheckBalanceAction() {
            return new Action("d867290d", Constants.CHECK_AIRTIME, "Check Airtime");
        }

        @Override
        public String getDisplayName() {
            return displayname;
        }

        @Override
        public String getId() {
            return id;
        }

        public int getImage() {
            return imageRes;
        }

        @Override
        public int getSlotIdx() {
            return slotIdx;
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
    public static class Mobile_9 implements NetworkImpl {

        private int slotIdx;
        String operatorName;
        String displayname;
        String id;
        boolean selected;
        int imageRes = R.drawable.mobile_9;

        public Mobile_9 (String operatorName, int slotIdx, String simId, String networkOperatorCode) {
            this.operatorName = operatorName;
            this.slotIdx = slotIdx;
            displayname = operatorName;
            this.id = simId;
        }

        @Override
        public String getNetworkOperatorCode() {
            return operatorName;
        }

        @Override
        public Action getCheckBalanceAction() {
            return new Action("d867290d", Constants.CHECK_AIRTIME, "Check Airtime");
        }

        @Override
        public String getDisplayName() {
            return displayname;
        }

        @Override
        public String getId() {
            return id;
        }

        public int getImage() {
            return imageRes;
        }

        @Override
        public int getSlotIdx() {
            return slotIdx;
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

    public ArrayList<NetworkItem.NetworkImpl> getNetworksFromSimInfos (List<SimInfo> simInfos) {

        ArrayList<NetworkItem.NetworkImpl> networks = new ArrayList<>();
        for (SimInfo siminfo : simInfos) {

            NetworkItem.Builder networkItemBuilder = new NetworkItem.Builder(siminfo.getOperatorName());
            if (networkItemBuilder.isSupportedSim()) {
                networks.add(networkItemBuilder.build(siminfo.slotIdx, siminfo.getNetworkOperator()));
            }
        }

        return networks;
    }

    public interface NetworkImpl {

        boolean isSelected();
        void setSelected(boolean selected);
        String getDisplayName();
        String getId();
        int getImage();
        int getSlotIdx();
        String getNetworkOperatorCode();
        Action getCheckBalanceAction();
    }

    public static class Action {

        private String actionID;
        private String name;
        private String code;
        private Action(String actionID, String code, String name) {
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

    private class Builder {
        private boolean isSupportedSim;
        private final String operatorName;
        private final String simId;

        public Builder(String operatorName) {
            this.operatorName = operatorName;
            simId = getSimId(operatorName);
            isSupportedSim = simId != null;
        }

        private String getSimId (String simName) {
            if (simName.toUpperCase().contains("MTN"))
                return Constants.MTN;
            else if (simName.toUpperCase().contains("AIRTEL") || simName.toUpperCase().contains("ZAIN") || simName.toUpperCase().contains("ECONET"))
                return Constants.AIRTEL;
            else if (simName.toUpperCase().contains("GLO") || simName.toUpperCase().contains("GLOBACOM"))
                return Constants.GLO;
            else if (simName.toUpperCase().contains("ETISALAT") || simName.toUpperCase().contains("9MOBILE") || simName.toUpperCase().contains("9 MOBILE") || simName.toUpperCase().contains("MOBILE9"))
                return Constants.MOBILE_9;
            return null;
        }

        public boolean isSupportedSim() {
            return isSupportedSim;
        }

        public NetworkImpl build(int slotIdx, String networkOperatorCode) {
            switch (simId) {
                case Constants.MTN:
                    return new Mtn(operatorName, slotIdx, simId, networkOperatorCode);
                case Constants.AIRTEL:
                    return new Airtel(operatorName, slotIdx, simId, networkOperatorCode);
                case Constants.GLO:
                    return new Glo(operatorName, slotIdx, simId, networkOperatorCode);
                case Constants.MOBILE_9:
                    return new Mobile_9(operatorName, slotIdx, simId, networkOperatorCode);
            }
            return null;
        }
    }
}
