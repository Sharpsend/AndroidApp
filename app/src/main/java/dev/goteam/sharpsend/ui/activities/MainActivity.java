package dev.goteam.sharpsend.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hover.sdk.actions.HoverAction;
import com.hover.sdk.api.Hover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

import dev.goteam.sharpsend.R;

public class MainActivity extends AppCompatActivity implements Hover.DownloadListener {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
             // menu should be considered as top level destinations.

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Initialize Hover
        Hover.initialize(getApplicationContext(), this);
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "Error while attempting to download actions, see logcat for error", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Error: " + s);
    }

    @Override
    public void onSuccess(ArrayList<HoverAction> arrayList) {
        //Toast.makeText(this, "Successfully downloaded " + arrayList.size() + " actions", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Successfully downloaded " + arrayList.size() + " actions");
    }

}
