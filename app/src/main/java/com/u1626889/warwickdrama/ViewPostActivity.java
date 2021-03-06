package com.u1626889.warwickdrama;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewPostActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int firstNum = intent.getIntExtra("postNumber",0);
        ArrayList<Post> posts = intent.getParcelableArrayListExtra("post");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), posts, posts.size()-1);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(firstNum);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_post, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_ID = "id";
        private static final String ARG_TITLE = "title";
        private static final String ARG_TYPE = "type";
        private static final String ARG_SOCIETY = "society";
        private static final String ARG_CONTACT = "contact";
        private static final String ARG_DESC = "desc";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, int id, String title, String type, String society, String contact, String desc) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt(ARG_ID, id);
            args.putString(ARG_TITLE, title);
            args.putString(ARG_TYPE, type);
            args.putString(ARG_SOCIETY, society);
            args.putString(ARG_CONTACT, contact);
            args.putString(ARG_DESC, desc);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_post, container, false);
            TextView typeText = (TextView) rootView.findViewById(R.id.typeText);
            TextView titleText = (TextView) rootView.findViewById(R.id.titleText);
            TextView contactText = (TextView) rootView.findViewById(R.id.contactText);
            final TextView descText = (TextView) rootView.findViewById(R.id.descText);
            final FloatingActionButton fab = rootView.findViewById(R.id.floatingActionButton);

            SharedPreferences prefs = inflater.getContext().getSharedPreferences("savedPosts",0);
            String savedPosts = prefs.getString("savedPosts","");

            // Determines if the saved icon should be lit or not, depending if the current post has already been saved
            ArrayList<String> saves = new ArrayList<>(Arrays.asList(savedPosts.split(",")));
            String id = Integer.toString(getArguments().getInt(ARG_ID));
            if(saves.contains(id)) fab.setImageResource(R.drawable.ic_save);
            else fab.setImageResource(R.drawable.ic_unsave);

            // Unfortunatly the on scroll listener only works at API level 23 and above
            // My min is 19, and I shouldn't increase it, 20% of users have API < 23
//            if(android.os.Build.VERSION.SDK_INT >= 23) {
//                descText.setOnScrollChangeListener(new TextView.OnScrollChangeListener()) {
//                    @Override
//                    public void onScrolled(TextView descText, int dx, int dy) {
//                        if( dy>0 || dy<0 && fab.isShown())
//                            fab.hide();
//                    }
//
//                    @Override
//                    public void onScrollStateChanged(TextView descText, int newState) {
//                        if (newState == TextView.SCROLL_STATE_IDLE)
//                            fab.show();
//                        super.onScrollStateChanged(descText, newState);
//                    }
//
//                }
//            }

//          TODO: Make the floating action button appear and dissapear when the user scrolls
            descText.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                int pos = 0;
                @Override
                public void onScrollChanged() {
                    int mScrollY = descText.getScrollY();
                    Log.d("thing","Scrolled! magnitude is "+mScrollY+", scroll mode is ");
                    if (mScrollY > 0 && fab.isShown()) {
                        fab.hide();
                    } else if (mScrollY < 0) {
                        fab.show();
                    }
                }
            });


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Gets the list of currently saved posts
                    SharedPreferences prefs = inflater.getContext().getSharedPreferences("savedPosts",0);
                    String savedPosts = prefs.getString("savedPosts","");
                    ArrayList<String> saves = new ArrayList<>(Arrays.asList(savedPosts.split(",")));
                    String id = Integer.toString(getArguments().getInt(ARG_ID));
                    SharedPreferences.Editor editor = prefs.edit();

                    // If the post is not saved, save it
                    // If the post is saved, unsave it
                    if(saves.contains(id)) {
                        saves.remove(id);
                        savedPosts = android.text.TextUtils.join(",", saves);

                        Snackbar.make(view, "Unsaved!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        fab.setImageResource(R.drawable.ic_unsave);
                    } else {
                        savedPosts += ","+id;

                        Snackbar.make(view, "Saved!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        fab.setImageResource(R.drawable.ic_save);
                    }

                    // Commits it rather than applies, cos it needs to happen immediately
                    Log.d("thing","SAVING POST, POSTS ARE NOW "+savedPosts);
                    editor.putString("savedPosts",savedPosts).commit();

                }
            });

            String title = getArguments().getString(ARG_TITLE);
            String type = getArguments().getString(ARG_TYPE);
            String society = getArguments().getString(ARG_SOCIETY);
            String contact = getArguments().getString(ARG_CONTACT);
            String desc = getArguments().getString(ARG_DESC);

            typeText.setText(type);
            titleText.setText(title);
            contactText.setText(contact);
            descText.setText(desc);
            descText.setMovementMethod(new ScrollingMovementMethod());

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        int postCount;
        ArrayList<Post> posts;
//        ArrayList<Integer> ids;
//        ArrayList<String> titles;
//        ArrayList<String> types;
//        ArrayList<String> socieites;
//        ArrayList<String> contacts;
//        ArrayList<String> descs;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Post> newPosts, int count) {
            super(fm);
            posts = newPosts;
            postCount = count+1;
//            ids = new ArrayList<Integer>(intarr);
//            titles = new ArrayList<String>(titlearr);
//            types = new ArrayList<String>(typearr);
//            socieites = new ArrayList<String>(societyarr);
//            contacts = new ArrayList<String>(contactarr);
//            descs= new ArrayList<String>(descarr);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position+1,posts.get(position).getId(),posts.get(position).getTitle(),posts.get(position).getType(),posts.get(position).getSociety(),posts.get(position).getOwner(),posts.get(position).getDesc());
        }

        @Override
        public int getCount() {
            return postCount;
        }
    }
}
