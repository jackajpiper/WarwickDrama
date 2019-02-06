package com.u1626889.warwickdrama;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CalandarActivity extends AppCompatActivity {

    // The adapter that contains the posts displayed in the recycler view
    private PostCalendarAdapter adapter;
    // Determines if the calendar shows all posts or just saved posts
    private boolean saves = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calandar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final Switch switchButton = findViewById(R.id.switch1);
        switchButton.setText("All posts");
        setSupportActionBar(toolbar);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saves = isChecked;
                Log.d("thing", "saved is "+saves);
                if(saves) {
                    switchButton.setText("Saved posts");
                }
                else {
                    switchButton.setText("All posts");
                }
            }
        });

        final int newColor = Color.parseColor("#891e1e");

        final ArrayList<CalendarDay> days = new ArrayList<>();

        final Context context = this;
        WDViewModel mViewModel = ViewModelProviders.of(this).get(WDViewModel.class);
        LiveData<List<Post>> dates = mViewModel.getAllPosts();
        dates.observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable final List<Post> dates){

                for(int i = 0; i < dates.size(); i++) {
                    String dateStr = dates.get(i).getExp_date();

                    // IT GOES IT GOES IT GOES IT GOES DD/MM/YYYY
                    ArrayList<String> date = new ArrayList<>(Arrays.asList(dateStr.split("/")));
                    CalendarDay day = CalendarDay.from(Integer.parseInt(date.get(2)), Integer.parseInt(date.get(1)), Integer.parseInt(date.get(0)));
                    days.add(day);
                }

                final EventDecorator eventDecorator = new EventDecorator(newColor, days);

                final MaterialCalendarView calendar = findViewById(R.id.calendarView);
                calendar.addDecorator(eventDecorator);

                adapter = new PostCalendarAdapter(context);
                adapter.setPosts((ArrayList) dates);
                calendar.setOnDateChangedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        String dateStr = date.getDate().toString();
                        adapter.filter(dateStr, saves);
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        saves = isChecked;
                        Log.d("thing", "saved is "+saves);
                        if(saves) {
                            switchButton.setText("Saved posts");
                            days.clear();

                            // Gets the list of currently saved posts
                            SharedPreferences prefs = context.getSharedPreferences("savedPosts",0);
                            String savedPosts = prefs.getString("savedPosts","");
                            ArrayList<String> savedIds = new ArrayList<>(Arrays.asList(savedPosts.split(",")));

                            for(int i = 0; i < dates.size(); i++) {
                                String dateStr = dates.get(i).getExp_date();
                                // IT GOES IT GOES IT GOES IT GOES DD/MM/YYYY
                                ArrayList<String> date = new ArrayList<>(Arrays.asList(dateStr.split("/")));
                                CalendarDay day = CalendarDay.from(Integer.parseInt(date.get(2)), Integer.parseInt(date.get(1)), Integer.parseInt(date.get(0)));
                                if(savedIds.contains(Integer.toString(dates.get(i).getId()))) days.add(day);
                            }

                            EventDecorator newDec = new EventDecorator(Color.parseColor("#cccc59"),days);
                            calendar.removeDecorators();
                            calendar.addDecorator(newDec);
                        }
                        else {
                            switchButton.setText("All posts");

                            for(int i = 0; i < dates.size(); i++) {
                                String dateStr = dates.get(i).getExp_date();
                                // IT GOES IT GOES IT GOES IT GOES DD/MM/YYYY
                                ArrayList<String> date = new ArrayList<>(Arrays.asList(dateStr.split("/")));
                                CalendarDay day = CalendarDay.from(Integer.parseInt(date.get(2)), Integer.parseInt(date.get(1)), Integer.parseInt(date.get(0)));
                                days.add(day);
                            }

                            EventDecorator newDec = new EventDecorator(newColor,days);
                            calendar.removeDecorators();
                            calendar.addDecorator(newDec);
                        }
                    }
                });

            }
        });

    }

    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(10, color));
        }
    }

}
