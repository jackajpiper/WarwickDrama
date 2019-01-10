package com.u1626889.warwickdrama;

import java.util.*;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<Integer> idArr = new ArrayList<Integer>();
    public static ArrayList<String> titleArr = new ArrayList<String>();
    public static ArrayList<String> typeArr = new ArrayList<String>();
    public static ArrayList<String> societyArr = new ArrayList<String>();
    public static ArrayList<String> contactArr = new ArrayList<String>();
    public static ArrayList<String> descArr = new ArrayList<String>();
    public static ArrayList<Boolean> expandArr = new ArrayList<Boolean>();
    // This variable holds the id of the cardview most recently added to the layout.
    // This allows the following cardview to chain itself under it
    public int mostRecentCardId;

    public ArrayList<Integer> getIds() {
        return idArr;
    }

    // The interface through which we access the database
    private WDViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Warwick Drama");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        TRYING THIS DATABASE THING, HERE GOES

        // TWO THINGS ARE ADDED TO THE DATABASE BUT THEY ARE NULL

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PostListAdapter adapter = new PostListAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Makes the divider to go between the items in the recycler view, change the gap by editing the method
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation()) {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == 0) {
                    return;
                }
                outRect.top = 15;
            }
        };

        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(WDViewModel.class);

        mViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                adapter.setPosts(new ArrayList<Post>(posts));
                adapter.notifyDataSetChanged();
            }
        });

        int i = adapter.getItemCount();


//               THIS SHOULD BE DELETED WHEN THE DATABASE IS MADE, RIGHT NOW IT JUST ADDS THE NEWLY CREATED POST TO THE LIST, THE POST SHOULD NOW BE IN THE DATABASE ANYWAY
        Intent intent = getIntent();
        if(intent.getStringExtra("title")!=null) {
            int id = intent.getIntExtra("id",0);
            String title = intent.getStringExtra("title");
            String contact = intent.getStringExtra("contact");
            String date = intent.getStringExtra("date");
            String description = intent.getStringExtra("description");
            String society = intent.getStringExtra("society");
            String type = intent.getStringExtra("type");
            String[] tags = intent.getStringArrayExtra("tags");
            idArr.add(id);
            titleArr.add(title);
            typeArr.add(type);
            societyArr.add(society);
            descArr.add(description);
            expandArr.add(false);
        }

//        for(int i = 0; i < idArr.size(); i++) {
//            final int num = i;
//            ConstraintLayout currentLayout = (ConstraintLayout) findViewById(R.id.cardViewLayout);
//            CardView card = createCard(currentLayout, i);
//            // Add an onclick listener to view the post in full
//
//            card.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, ViewPostActivity.class);
//                    intent.putExtra("postNumber", num);
//                    startActivity(intent);
//                }
//            });
//            // Finally, add the CardView in the correct layout
//            currentLayout.addView(card);
//        }

    }

    // TODO - this should run when the reply activity returns with the data for a new post
    // see https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#11
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Post
//    }

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
        if (id == R.id.codpeice) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String title = item.getTitle().toString();
        // Runs the method to only show posts related to that menu item
        switch (title) {

            case "Create a note":
                Intent intent = new Intent(this, CreatePostActivity.class);
                startActivity(intent);
                break;
                // TODO there are a bunch of these commented out sections - replace them as necessary
//            case "Codpeice": societySpecific("Codpeice");break;
//            case "Freshblood": societySpecific("Freshblood");break;
//            case "Musical Theatre Warwick": societySpecific("MTW");break;
//            case "Opera Warwick": societySpecific("OpWa");break;
//            case "ShakeSoc": societySpecific("ShakeSoc");break;
//            case "Tech Crew": societySpecific("TechCrew");break;
//            case "Warwick Improvised Theatre Society": societySpecific("WITS");break;
//            case "Warwick University Drama Society": societySpecific("WUDS");break;
//            default: societySpecific("");break;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(title.equals("Home")) toolbar.setTitle("Warwick Drama");
        else toolbar.setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Method to create a card. Refers to the method to create the internal layout of a card
    public CardView createCard(ConstraintLayout currentLayout, int i) {
        // Set the CardView layoutParams and define how it will be arranged on the screen
        CardView card = new CardView(this);
        // THE FUNCTION View.generateViewId() WILL BE USED WHEN THE POST IS CREATED, SO idArr WILL HAVE A UNIQUE ID ALREADY, DON'T WORRY.
        card.setId(idArr.get(i));
        card.setTag("cardView"+idArr.get(i));
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(30, 30, 30, 30);
        if(mostRecentCardId == -1) {
            params.topToTop = currentLayout.getId();
            mostRecentCardId = card.getId();
        }
        else {
            params.topToBottom = mostRecentCardId;
            mostRecentCardId = card.getId();
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

        // Create the layout to go inside the card, and add it to the card
        ConstraintLayout insideLayout = makeCardInside(idArr.get(i),titleArr.get(i),typeArr.get(i),societyArr.get(i),descArr.get(i));
        card.addView(insideLayout);
        return card;
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

        final ImageButton button = new ImageButton(this);
        button.setBackgroundResource(R.drawable.ic_expand_card);
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
                    button.setBackgroundResource(R.drawable.ic_collapse_card);
                    expandArr.set(arrayNum,true);
                } else {
                    tv.setText(society + "\n"+ type + "\n\n" + title);
                    button.setBackgroundResource(R.drawable.ic_expand_card);
                    expandArr.set(arrayNum,false);
                }
            }
        });

        // Put the TextView in CardView
        insideLayout.addView(tv);
        insideLayout.addView(button);
        return insideLayout;
    }


    public void societySpecific(String society) {
        ConstraintLayout currentLayout = (ConstraintLayout) findViewById(R.id.cardViewLayout);
        currentLayout.removeAllViews();
        mostRecentCardId = -1;

        // The society is "" when the user clicks 'Home', so add all the posts
        if(society.equals("")) {
            for(int i = 0; i < idArr.size(); i++) {
                CardView card = createCard(currentLayout, i);
                currentLayout.addView(card);
            }
        } else {
            // Otherwise, only add posts which are part of the selected society
            for(int i = 0; i < idArr.size(); i++) {
                if(societyArr.get(i).equals(society)) {
                    CardView card = createCard(currentLayout, i);
                    // Finally, add the CardView in the correct layout
                    currentLayout.addView(card);
                }
            }
        }

    }

}
