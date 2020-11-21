package dev.goteam.sharpsend.db.entities;

public class Action {

    private final String type;
    private final String actionID;
    private final String name;
    private final String code;
    private boolean isSelected;

    public Action(String actionID, String code, String name, String type) {
        isSelected = false;
        this.actionID = actionID;
        this.name = name;
        this.code = code;
        this.type = type;
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