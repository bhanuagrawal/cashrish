package com.ultimatix.cashrich;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.ultimatix.cashrich.ui.FragmentInteractionListner;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements FragmentInteractionListner  {

    NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
    }

    @Override
    public void openPage(NavDirections action) {
        navHostFragment.getNavController().navigate(action);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("newsapp mainactivity", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("newsapp mainactivity", "onDestroy");

    }
}
