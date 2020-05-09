package dev.goteam.paydrift.db.entities;

public class HomeItem {
    private String title;
    private String description;
    private int iconResID;
    private String type;

    public HomeItem(String title, String description, int iconResID, String type) {
        this.title = title;
        this.description = description;
        this.iconResID = iconResID;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResID() {
        return iconResID;
    }

    public String getType() {
        return type;
    }
}
