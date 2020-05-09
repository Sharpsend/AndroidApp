package dev.goteam.sharpsend.db.entities;

import androidx.annotation.DrawableRes;

public class Bank {

    @DrawableRes
    private int imageRes;

    private String bankName;

    private boolean isSelected;

    public Bank(int imageRes, String bankName, boolean isSelected) {
        this.imageRes = imageRes;
        this.bankName = bankName;
        this.isSelected = isSelected;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getBankName() {
        return bankName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
