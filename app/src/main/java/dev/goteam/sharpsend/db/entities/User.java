package dev.goteam.sharpsend.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userID;
    private String userName;
    private NetworkItem.NetworkImpl defaultSim;

    public User(String userName) {
        this.userName = userName;
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

    public NetworkItem.NetworkImpl getDefaultSim() {
        return defaultSim;
    }

    public void setDefaultSim(NetworkItem.NetworkImpl defaultSim) {
        this.defaultSim = defaultSim;
    }
}
