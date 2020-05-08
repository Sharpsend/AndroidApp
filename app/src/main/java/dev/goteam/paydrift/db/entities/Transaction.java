package dev.goteam.paydrift.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int txID;

    private String txType;
    private Double amount;
    //private String recipient;
    private String state;
    private String timeStamp;

    public Transaction(int txID, String txType, Double amount, String state, String timeStamp) {
        this.txID = txID;
        this.txType = txType;
        this.amount = amount;
        this.state = state;
        this.timeStamp = timeStamp;
    }

    public int getTxID() {
        return txID;
    }

    public String getTxType() {
        return txType;
    }

    public Double getAmount() {
        return amount;
    }

    public String getState() {
        return state;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getDate() {
        return timeStamp;
    }

    public String getTime() {
        return timeStamp;
    }
}
