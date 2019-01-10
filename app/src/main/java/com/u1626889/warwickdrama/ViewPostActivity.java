package com.u1626889.warwickdrama;

import android.content.Intent;
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

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Intent intent = getIntent();
        int firstNum = intent.getIntExtra("postNumber",0);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Post> posts = bundle.getParcelable("posts");



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), posts);


        mViewPager.setCurrentItem(firstNum);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // TODO: implement 'saved' functionality from here
//        final ImageView save_button = findViewById(R.id.imageView3);
//        save_button.setImageResource(R.drawable.ic_unsave);
//        final boolean saved = false;
//        save_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(saved) {
//                    save_button.setImageResource(R.drawable.ic_save);
//                }
//            }
//        });

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_post, container, false);
            TextView typeText = (TextView) rootView.findViewById(R.id.typeText);
            TextView titleText = (TextView) rootView.findViewById(R.id.titleText);
            TextView contactText = (TextView) rootView.findViewById(R.id.contactText);
            TextView descText = (TextView) rootView.findViewById(R.id.descText);

            int id = getArguments().getInt(ARG_ID);
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

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Post> newPosts) {
            super(fm);
            posts = newPosts;
//            postCount = count;
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
            return PlaceholderFragment.newInstance(position + 1,posts.get(position).getId(),posts.get(position).getTitle(),posts.get(position).getType(),posts.get(position).getSociety(),posts.get(position).getOwner(),posts.get(position).getDesc());
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return postCount;
        }
    }
}
