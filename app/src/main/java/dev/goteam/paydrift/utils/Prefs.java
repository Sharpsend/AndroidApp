package dev.goteam.paydrift.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static String AUTHENTICATED = "authenticated";
    private static String PREFS_NAME = "sharpsend_prefs";
    private static String USERNAME_KEY = "username";
    private static String PIN_KEY = "pin";

    public static boolean isAuthenticated(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getBoolean(AUTHENTICATED, false);
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

}
