package dev.goteam.sharpsend.db.entities;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

import dev.goteam.sharpsend.utils.Constants;

public class NetworkItem {

    public static class Network implements NetworkImpl {

        String name;
        String id;
        boolean selected;

        @DrawableRes
        int imageRes;

        public Network(String name, String id, @DrawableRes int imageRes) {
            this.name = name;
            this.id = id;
            this.imageRes = imageRes;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getId() {
            return id;
        }

        public int getImage() {
            return imageRes;
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

    public ArrayList<NetworkItem.Network> getMobiles() {
        ArrayList<NetworkItem.Network> networks = new ArrayList<>();

        return networks;
    }

    public interface NetworkImpl {

        boolean isSelected();
        void setSelected(boolean selected);
        String getName();
        String getId();
        int getImage();
    }

}
