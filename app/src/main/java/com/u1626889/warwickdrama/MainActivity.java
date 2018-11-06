package com.u1626889.warwickdrama;

import java.util.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public ArrayList<Integer> idArr = new ArrayList<Integer>();
    public ArrayList<String> titleArr = new ArrayList<String>();
    public ArrayList<String> typeArr = new ArrayList<String>();
    public ArrayList<String> societyArr = new ArrayList<String>();
    public ArrayList<String> descArr = new ArrayList<String>();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        idArr.add(1);
        idArr.add(2);
        idArr.add(3);
        idArr.add(4);
        titleArr.add("WITS Presents: A Play auditions");
        titleArr.add("oi get fucked i kiss dogs");
        titleArr.add("Isn't it weird");
        titleArr.add("My Minecraft account got deleted");
        typeArr.add("Audition");
        typeArr.add("Misc");
        typeArr.add("Workshop");
        typeArr.add("Misc");
        societyArr.add("WITS");
        societyArr.add("MTW");
        societyArr.add("Codpeice");
        societyArr.add("Freshblood");
        descArr.add("Come audition for Warwick Improv's edinburgh show, tentatively entitled 'A Play'! The audition will consist of a series of short form games.");
        descArr.add("oi get fucked i kiss dogs");
        descArr.add("Isn't it weird how you feet smell and your nose runs but your dad doesn't love you");
        descArr.add("My Minecraft account got deleted and now I can't even watch youtube song parodies without crying");




        // Get the application context
        mContext = getApplicationContext();

        TextView tv = (TextView)findViewById(R.id.info_text);
        tv.setText(titleArr.get(0));

        final ConstraintLayout mConstraintLayout;

        ImageButton button = findViewById(R.id.imageButton);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.cardViewLayout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for(int i = 0; i < idArr.size(); i++) {
                    CardView card = new CardView(mContext);

                    // Set the CardView layoutParams
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(15, 15, 15, 15);
                    if(i == 0) {
                        params.topToBottom = R.id.card_view9;
                    } else {
                        params.topToBottom = idArr.get(i-1);
                    }

                    card.setLayoutParams(params);

                    // Set CardView corner radius
                    card.setRadius(4);

                    // Set cardView content padding
                    card.setContentPadding(15, 15, 15, 15);

                    // Set a background color for CardView
                    card.setCardBackgroundColor(Color.LTGRAY);

                    // Set the CardView maximum elevation
                    card.setMaxCardElevation(15);

                    // Set CardView elevation
                    card.setCardElevation(9);

                    card.setId(idArr.get(i));

                    // Initialize a new TextView to put in CardView
                    TextView tv = new TextView(mContext);
                    tv.setLayoutParams(params);
                    tv.setText(societyArr.get(i) + "\n"+ typeArr.get(i) + "\n\n" + titleArr.get(i) + "\n\n" + idArr.get(i));
                    tv.setTextColor(Color.RED);

                    // Put the TextView in CardView
                    card.addView(tv);

                    // Finally, add the CardView in root layout
                    mConstraintLayout.addView(card);
                }

            }
        });

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
