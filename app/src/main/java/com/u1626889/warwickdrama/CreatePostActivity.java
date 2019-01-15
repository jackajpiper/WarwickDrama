package com.u1626889.warwickdrama;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Data"));
        tabLayout.addTab(tabLayout.newTab().setText("Tags"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabPagerAdapter adapter = new TabPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        popupDate(tabLayout);

    }


    public void popupDate(TabLayout tabLayout) {

        int tv = (int)(((LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(0)).getChildCount());
        Log.d("thing", "thing pleeeeeeeeeese " + tv);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
























    /** Called when the user taps the Send button */
    // CARRIES ALL OF THE DATA FROM THE FIRST CREATE POST ACTIVITY
//    public void progress(View view) {
////        Intent intent = new Intent(this, CreatePost2Activity.class);
//        EditText titleText = (EditText) findViewById(R.id.title_text);
//        EditText contactText = (EditText) findViewById(R.id.contact_text);
//        EditText dateText = (EditText) findViewById(R.id.dateText);
//        EditText descriptionText = (EditText) findViewById(R.id.descriptionText);
//        Spinner societySpinner = (Spinner) findViewById(R.id.societies_spinner);
//        Spinner typeSpinner = (Spinner) findViewById(R.id.type_spinner);
//
//        String title = titleText.getText().toString();
//        String contact = contactText.getText().toString();
//        String date = dateText.getText().toString();
//        String description = descriptionText.getText().toString();
//        String society = societySpinner.getSelectedItem().toString();
//        String type = typeSpinner.getSelectedItem().toString();
//
////        intent.putExtra("title", title);
////        intent.putExtra("contact", contact);
////        intent.putExtra("date", date);
////        intent.putExtra("description", description);
////        intent.putExtra("society", society);
////        intent.putExtra("type", type);
//
//        Intent replyIntent = new Intent();
//        if (false) {
//            // TODO - Finish adding validation to the user input
//            setResult(RESULT_CANCELED, replyIntent);
//        } else {
//            int newId = View.generateViewId();
//            replyIntent.putExtra("id",newId);
//            replyIntent.putExtra("title", title);
//            replyIntent.putExtra("contact", contact);
//            replyIntent.putExtra("date", date);
//            replyIntent.putExtra("description", description);
//            replyIntent.putExtra("society", society);
//            replyIntent.putExtra("type", type);
//            replyIntent.putExtra("tags", "this,that");
//
//            Intent intent = new Intent(this, CreatePost2Activity.class);
//
//            setResult(RESULT_OK, replyIntent);
//            Log.d("thing", "Packing the CreatePost2Activity reply with data");
//        }
//        finish();
//
//
////        startActivity(intent);
//    }
