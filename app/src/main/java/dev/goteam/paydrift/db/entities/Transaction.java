package dev.goteam.paydrift.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private String txType;
    private Double amount;
    //private String recipient;
    private String state;
    private String timeStamp;
    private String date;
    private String time;

    public Transaction(String txType, Double amount, String state, String timeStamp) {
        this.txType = txType;
        this.amount = amount;
        this.state = state;
        this.timeStamp = timeStamp;
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
        return date;
    }

    public String getTime() {
        return time;
    }
}
