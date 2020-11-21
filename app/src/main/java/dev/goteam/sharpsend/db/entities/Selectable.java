package dev.goteam.sharpsend.db.entities;

import java.util.ArrayList;

import dev.goteam.sharpsend.utils.Constants;

public class Selectable {

    public class Self implements Item {

        String name;
        String id;
        boolean selected;

        public Self(String name, String id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getId() {
            return id;
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

    public class ThirdParty implements Item {

        String name;
        String id;
        boolean selected;

        public ThirdParty(String name, String id) {
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

    public ArrayList<Item> getMobileItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Self("Self", Constants.MOBILE_NUMBER_SELF));
        items.add(new ThirdParty("Third Party", Constants.MOBILE_NUMBER_THIRD_PARTY));

        return items;
    }

    public interface Item {

        boolean isSelected();
        void setSelected(boolean selected);
        String getName();
        String getId();
    }

}
