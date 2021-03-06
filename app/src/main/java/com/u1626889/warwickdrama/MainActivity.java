package com.u1626889.warwickdrama;

import java.util.*;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // This is for the onActivityResult method working with newly user created posts
    public static final int NEW_POST_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_TAGS_ACTIVITY_REQUEST_CODE = 7;

    // The interface through which we access the database
    private WDViewModel mViewModel;
    // The adapter that contains the posts displayed in the recycler view
    private PostListAdapter adapter;

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
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new PostListAdapter(this);
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

        PostDividerItemDecoration divider = new PostDividerItemDecoration(recyclerView.getContext(),R.drawable.post_divider);

//        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(WDViewModel.class);

        mViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                Collections.sort(posts, new PostComparator(getApplicationContext(), posts));
                adapter.setPosts(new ArrayList<Post>(posts));
                adapter.notifyDataSetChanged();
            }
        });

    }

    // TODO - this should run when the reply activity returns with the data for a new post
    // see https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#11
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_POST_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Post post = new Post(data.getIntExtra("id", 0), data.getStringExtra("title"), data.getStringExtra("contact"), data.getStringExtra("type"), data.getStringExtra("society"), data.getStringExtra("description"), data.getStringExtra("tags"), data.getStringExtra("date"));
            mViewModel.insert(post);
        } else if (requestCode == EDIT_TAGS_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            String tags = data.getStringExtra("new_tags");

            //  PUTS THE USER'S TAGS INTO THE APP'S DATA SO IT CAN BE REFERENCED ELSEWHERE IN THE APP
            SharedPreferences prefs = getSharedPreferences("user_tags",0);
            SharedPreferences.Editor editor = prefs.edit();
            // Commits it rather than applies, cos it needs to happen immediately
            editor.putString("user_tags",tags).commit();

            ArrayList<Post> newsorted = new ArrayList<>(adapter.getPosts());

            Collections.sort(newsorted, new PostComparator(getApplicationContext(), newsorted));

            adapter.setPosts(newsorted);
            adapter.notifyDataSetChanged();

            Toast.makeText(
                    getApplicationContext(),
                    "Tags updated!",
                    Toast.LENGTH_LONG).show();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Warwick Drama");
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
        // Getting SearchView from XML layout by id defined there - action_search in this case
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        Log.d("thing",""+searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // your text view here
                adapter.filter(newText,"search");
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query,"search");
                searchView.clearFocus();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
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

            case "Create a post":
                Log.d("thing","Trying to create a post");
                Intent intent = new Intent(this, CreatePostActivity.class);
                startActivityForResult(intent, NEW_POST_ACTIVITY_REQUEST_CODE);
                break;
            case "Edit my tags":
                Intent tagsintent = new Intent(this, EditTagsActivity.class);
                startActivityForResult(tagsintent, EDIT_TAGS_ACTIVITY_REQUEST_CODE);
                break;
                // TODO there are a bunch of these commented out sections - replace them as necessary
            case "Codpeice": adapter.filter("Codpeice", "society");break;
            case "Freshblood": adapter.filter("Freshblood", "society");break;
            case "Musical Theatre Warwick": adapter.filter("Musical Theatre Warwick", "society");break;
            case "Opera Warwick": adapter.filter("Opera Warwick", "society");break;
            case "ShakeSoc": adapter.filter("ShakeSoc", "society");break;
            case "Tech Crew": adapter.filter("Tech Crew", "society");break;
            case "Warwick Improvised Theatre Society": adapter.filter("Warwick Improvised Theatre Society", "society");break;
            case "Warwick University Drama Society": adapter.filter("Warwick University Drama Society", "society");break;
            case "Calendar":
                intent = new Intent(this, CalandarActivity.class);
                startActivity(intent);
                break;
            case "My saved posts": adapter.filter("","saved");break;
            default: adapter.filter("", "society");break;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if(title.equals("Home")) toolbar.setTitle("Warwick Drama");
//        else if(title.equals("Settings")) toolbar.setTitle();
        if(!title.equals("Home") && !title.equals("Calendar") && !title.equals("Settings") && !title.equals("Administrator Settings")) toolbar.setTitle(title);
        else toolbar.setTitle("Warwick Drama");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
