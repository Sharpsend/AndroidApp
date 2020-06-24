package dev.goteam.sharpsend.db.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userID;
    private String userName;
    private int slotIdx;

    public User(String userName) {
        this.userName = userName;
        this.slotIdx = 0;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public int getSlotIdx() {
        return slotIdx;
    }

    public void setSlotIdx(int slotIdx) {
        this.slotIdx = slotIdx;
    }
}
