package dev.goteam.sharpsend.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static String AUTHENTICATED = "authenticated";
    private static String PREFS_NAME = "sharpsend_prefs";
    private static String USERNAME_KEY = "username";
    private static String FINGERPRINT_KEY = "fingerprint";
    private static String PIN_KEY = "pin";

    public static boolean isAuthenticated(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getBoolean(AUTHENTICATED, false);
    }

    public static boolean changePIN(Context ctx, String oldPIN, String newPIN) {
        boolean changed = false;
        SharedPreferences sharedPreferences = getPreferences(ctx);

        String actualPIN = sharedPreferences.getString(PIN_KEY, "");
        if (actualPIN.equals(oldPIN)) {
            setPIN(ctx, newPIN);
            changed = true;
        }

        return changed;
    }

    public static void setPIN(Context ctx, String newPIN) {
        SharedPreferences sharedPreferences = getPreferences(ctx);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PIN_KEY, newPIN);
        editor.commit();
    }

    public static String[] getUserData(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);

        String PIN = sharedPreferences.getString(PIN_KEY, "");
        String username = sharedPreferences.getString(USERNAME_KEY, "");

        return new String[]{ PIN, username };
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void saveUser(Context context, String username, String PIN) {
        SharedPreferences sharedPreferences = getPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.putString(PIN_KEY, PIN);

        editor.putBoolean(AUTHENTICATED, true);

        editor.apply();
    }

    public static boolean isFingerprintEnabled(Context ctx) {
        SharedPreferences sharedPreferences = getPreferences(ctx);

        return sharedPreferences.getBoolean(FINGERPRINT_KEY, false);
    }

    public static void setFingerprintState(Context context, boolean state) {
        SharedPreferences sharedPreferences = getPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FINGERPRINT_KEY, state);

        editor.apply();
    }

}
