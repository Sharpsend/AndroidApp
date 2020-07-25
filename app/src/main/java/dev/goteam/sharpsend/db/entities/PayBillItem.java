package dev.goteam.sharpsend.db.entities;

public class PayBillItem {
    private String title;
    private String id;

    public PayBillItem(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}

