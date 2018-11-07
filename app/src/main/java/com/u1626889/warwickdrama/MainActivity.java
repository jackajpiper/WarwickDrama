package com.u1626889.warwickdrama;

import java.util.*;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
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
    public ArrayList<Boolean> expandArr = new ArrayList<Boolean>();
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
        idArr.add(5);
        idArr.add(6);
        idArr.add(7);
        idArr.add(8);
        titleArr.add("WITS Presents: A Play auditions");
        titleArr.add("Rent fundraiser!");
        titleArr.add("A View From The Bridge");
        titleArr.add("Producer needed! Behind the Eyes");
        titleArr.add("WUDS and improv and you");
        titleArr.add("Freshblood Views The Bridge!");
        titleArr.add("Death Of An Anarchist auditions");
        titleArr.add("Be on the DC panel!");
        typeArr.add("Audition");
        typeArr.add("Social");
        typeArr.add("Show");
        typeArr.add("Production Opportunity");
        typeArr.add("Workshop");
        typeArr.add("Social");
        typeArr.add("Audition");
        typeArr.add("Announcement");
        societyArr.add("WITS");
        societyArr.add("MTW");
        societyArr.add("WUDS");
        societyArr.add("Codpeice");
        societyArr.add("WUDS");
        societyArr.add("Freshblood");
        societyArr.add("WUDS");
        societyArr.add("Other");
        descArr.add("Come audition for Warwick Improv's edinburgh show, tentatively entitled 'A Play'! The audition will consist of a series of short form games.");
        descArr.add("Rent is having a fundraiser, come support this terrible show. Try not to think about how Angel kills dogs.");
        descArr.add("A View From The Bridge is a crackin ply with accents and a chair. Come see.");
        descArr.add("behind the eyes needs an assistant producer, and that assistant producer that behind the eyes needs, it could be you.");
        descArr.add("WUDS are running a worskhop on improv, and they've used WITS as a way of injecting a bit of that.");
        descArr.add("Your lovely freshblood social secs are gonna be at the duck at 6pm practicing our accents before jetting off to see WUDS' latest production - A View From The Bridge!");
        descArr.add("Be an actor! Not an acorn. Act as the lead. Or as a support. Yay.");
        descArr.add("Would YOU like to be the sole arbiter of people's hopes and dreams? Well, this is the next best thing I promise.");
        expandArr.add(false);
        expandArr.add(false);
        expandArr.add(false);
        expandArr.add(false);
        expandArr.add(false);
        expandArr.add(false);
        expandArr.add(false);
        expandArr.add(false);


        for(int i = 0; i < idArr.size(); i++) {
            CardView card = new CardView(this);
            ConstraintLayout currentLayout = (ConstraintLayout) findViewById(R.id.cardViewLayout);

            // Set the CardView layoutParams and define how it will be arranged on the screen
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(30, 30, 30, 30);
            if(i == 0) {
                params.topToTop = currentLayout.getId();
            } else {
                params.topToBottom = idArr.get(i-1);
            }
            card.setLayoutParams(params);
            // Set CardView corner radius
            card.setRadius(4);
            // Set cardView content padding
            card.setContentPadding(15, 15, 15, 15);
            // Set a background color for CardView
            card.setCardBackgroundColor(Color.parseColor("#0dd6dd"));
            // Set the CardView maximum elevation
            card.setMaxCardElevation(15);
            // Set CardView elevation
            card.setCardElevation(9);
            // THE FUNCTION View.generateViewId() WILL BE USED WHEN THE POST IS CREATED, SO idArr WILL HAVE A UNIQUE ID ALREADY, DON'T WORRY.
            card.setId(idArr.get(i));
            card.setTag("cardView"+idArr.get(i));


            ConstraintLayout insideLayout = makeCardInside(idArr.get(i),titleArr.get(i),typeArr.get(i),societyArr.get(i),descArr.get(i));


            card.addView(insideLayout);

            // Finally, add the CardView in the correct layout
            currentLayout.addView(card);
        }

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

    // This method creates the layout that will go inside a CardView, given the required data
    public ConstraintLayout makeCardInside(final int cardid, final String title, final String type, final String society, final String desc) {

        // Define a new layout
        // This is the layout inside the cardview. All the stuff is in here, then this is added to the cardview
        final ConstraintLayout insideLayout = new ConstraintLayout(this);
        // Need to generate a new unique ID for the layout
        int newid = View.generateViewId();
        insideLayout.setId(newid);

        // Create the layout parameters for each view including the layout
        final ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        // Create the layout parameters for each view including the layout
        final ConstraintLayout.LayoutParams IMGparams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        // Create the layout parameters for each view including the layout
        final ConstraintLayout.LayoutParams TVparams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        insideLayout.setLayoutParams(params);

        ImageButton button = new ImageButton(this);
        button.setBackgroundResource(R.drawable.ic_menu_send);
        // Define the layout parameters for the image button
        IMGparams.height = 120;
        IMGparams.width = 120;
        IMGparams.endToEnd = insideLayout.getId();
        IMGparams.topToTop = insideLayout.getId();
        button.setLayoutParams(IMGparams);


        // Initialize a new TextView to put in CardView
        final TextView tv = new TextView(this);
        // Need to generate a new unique ID for the view
        newid = View.generateViewId();
        tv.setId(newid);
        tv.setText(society + "\n"+ type + "\n\n" + title);
        tv.setTextColor(Color.BLACK);
        TVparams.endToStart = button.getId();
        tv.setLayoutParams(TVparams);

        // Add an onclick event so that clicking the button expands and collapses the text
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int arrayNum = idArr.indexOf(cardid);
                tv.setText(" "+arrayNum);
                if(expandArr.get(arrayNum) == false) {
                    tv.setText(society + "\n"+ type + "\n\n" + title + "\n\n" + desc);
                    expandArr.set(arrayNum,true);
                } else {
                    tv.setText(society + "\n"+ type + "\n\n" + title);
                    expandArr.set(arrayNum,false);
                }
            }
        });

        // Put the TextView in CardView
        insideLayout.addView(tv);
        insideLayout.addView(button);
        return insideLayout;
    }

}
