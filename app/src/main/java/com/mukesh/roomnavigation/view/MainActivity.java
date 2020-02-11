package com.mukesh.roomnavigation.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mukesh.roomnavigation.R;

/**
 * Simple Launcher Activity having Navigation Controller
 */
public class MainActivity extends AppCompatActivity {

    //Navigation Controller to control the fragment stacks
    private NavController navController;
    //To enable clicking back button
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        setupActionBar();
    }

    private void setupActionBar() {
        //we can add custom toolbar in main activity
        //To handle Title for fragments
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //To support back button click
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }

}
