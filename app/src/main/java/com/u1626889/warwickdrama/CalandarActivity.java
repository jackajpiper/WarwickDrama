package com.u1626889.warwickdrama;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CalandarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calandar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int newColor = Color.parseColor("#891e1e");

        final ArrayList<CalendarDay> days = new ArrayList<>();

        WDViewModel mViewModel = ViewModelProviders.of(this).get(WDViewModel.class);
        LiveData<List<Post>> dates = mViewModel.getAllPosts();
        dates.observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> dates){

                for(int i = 0; i < dates.size(); i++) {

                    String dateStr = dates.get(i).getExp_date();
                    Log.d("thing","date is "+dateStr);

                    // IT GOES IT GOES IT GOES IT GOES DD/MM/YYYY
                    ArrayList<String> date = new ArrayList<>(Arrays.asList(dateStr.split("/")));

                    Log.d("thing", "test says "+Integer.parseInt(date.get(2))+"/"+Integer.parseInt(date.get(1))+"/"+Integer.parseInt(date.get(0)));

                    CalendarDay day = CalendarDay.from(Integer.parseInt(date.get(2)), Integer.parseInt(date.get(1)), Integer.parseInt(date.get(0)));
                    days.add(day);

                    Log.d("thing","days size is "+days.size());
                    EventDecorator eventDecorator = new EventDecorator(newColor, days);

                    MaterialCalendarView calendar = findViewById(R.id.calendarView);

                    calendar.addDecorator(eventDecorator);

                }

            }
        });



//        ArrayList<CalendarDay> days = new ArrayList<CalendarDay>();
//        days.add(day);



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
            view.addSpan(new DotSpan(5, color));
        }
    }

}
