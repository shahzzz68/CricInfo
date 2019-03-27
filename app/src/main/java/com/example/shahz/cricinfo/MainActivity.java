package com.example.shahz.cricinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shahz.cricinfo.fragments.Alerts;
import com.example.shahz.cricinfo.fragments.Live_Scores;
import com.example.shahz.cricinfo.fragments.Players;
import com.example.shahz.cricinfo.fragments.Results;
import com.example.shahz.cricinfo.fragments.Video_Gallery;
import com.example.shahz.cricinfo.fragments.upComming_matches;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("news");

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            //NAVIGATION VIEW OF DRAWER AND LISTNER
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            // FRAGMENT REPLACEMENT WHEN FIRST TIME LOADED
            if (savedInstanceState == null) {
                replaceFragment(new upComming_matches());
                navigationView.setCheckedItem(R.id.upComming_Matches);
            }

    }

    boolean isPressed=true;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            drawer.openDrawer(GravityCompat.START);


            if (drawer.isDrawerOpen(GravityCompat.START)) {

                if (isPressed == true) {
                    isPressed = false;
                    Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

                    new CountDownTimer(1000, 2000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {

                            isPressed = true;
                        }
                    }.start();

                } else {
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        super.onBackPressed();
                    }

                }
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
        if (id==R.id.upload_video_icon);
        {
            Intent intent = new Intent(MainActivity.this,Upload_Video_activity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.upComming_Matches:
                replaceFragment(new upComming_matches());
                getSupportActionBar().setTitle("Upcomming Matches");
                break;
            case R.id.results:
                replaceFragment(new Results());
                getSupportActionBar().setTitle("Results");
                break;
            case R.id.notification:
                replaceFragment(new Alerts());
                getSupportActionBar().setTitle("Alerts");
                break;
            case R.id.live_scores:
                replaceFragment(new Live_Scores());
                getSupportActionBar().setTitle("Live Scores");
                break;
            case R.id.players:
                replaceFragment(new Players());
                getSupportActionBar().setTitle("Players");
                break;
            case R.id.videoGallery:
                replaceFragment(new Video_Gallery());
                getSupportActionBar().setTitle("Video Gallery");
                break;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout,fragment).commit();

    }



}
