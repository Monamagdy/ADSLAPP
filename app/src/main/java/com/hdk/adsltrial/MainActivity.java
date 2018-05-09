package com.hdk.adsltrial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.hdk.adsltrial.router.RouterCommands;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean connected = false;

     Switch switch2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar)
        switch2 = findViewById(R.id.switch_);
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setMax(150);
        bar.setScaleY(4f);
        bar.setProgress(70);
        FloatingActionButton fab = findViewById(R.id.fab);
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                setStatus();
                return null;
            }
        }.execute();
        //bundle details : Unlimited 2Mbps
        //quota : 150 GB
        // Remaining days : 12
        //ADSL Account Balance:
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Context mContext = MainActivity.this;
                Dialog dialog = new Dialog(mContext);

                dialog.setContentView(R.layout.activity_feedback);
                dialog.setTitle("Rate our service!");
                dialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setup) {
            Intent intent = new Intent(MainActivity.this, SetupRouterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_troubleshoot) {
            Intent intent = new Intent(MainActivity.this, TroubleshootActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static class MyDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            // Inflate the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            View view = inflater.inflate(R.layout.activity_feedback, null);

            // Get your views by using view.findViewById() here and do your listeners.

            // Set the dialog layout
            builder.setView(view);

            return builder.create();
        }

    }
    private void setStatus() {

        try {
            RouterCommands router = RouterFactory.getInstance();
            connected = true;
        } catch(Throwable dexp) {

        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch2.setChecked(connected);
            }
        });

    }

}
