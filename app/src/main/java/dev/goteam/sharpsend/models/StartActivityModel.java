package dev.goteam.sharpsend.models;

import android.content.Intent;

public class StartActivityModel {
    private Intent intent;
    private int requestCode;

    public StartActivityModel(Intent intent, int requestCode) {
        this.intent = intent;
        this.requestCode = requestCode;
    }

    public Intent getIntent() {
        return intent;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
