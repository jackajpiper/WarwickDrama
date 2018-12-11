package com.u1626889.warwickdrama;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Create a post");

        // Adds the functionality to make the datepicker pop up when the date exit text box is clicked
        popupDatePicker();



    }

    private void popupDatePicker() {
        final Context context = this;
        final Calendar myCalendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        EditText contact_text = (EditText) findViewById(R.id.dateText);

        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        contact_text.setText(dateString);

        EditText edittext= (EditText) findViewById(R.id.dateText);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
//                 TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                EditText contact_text = (EditText)findViewById(R.id.dateText);
                contact_text.setText(sdf.format(myCalendar.getTime()));
            }

        };

        // onclick - popup datepicker
        contact_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /** Called when the user taps the Send button */
    // CARRIES ALL OF THE DATA FROM THE FIRST CREATE POST ACTIVITY
    public void progress(View view) {
        Intent intent = new Intent(this, CreatePost2Activity.class);
        EditText titleText = (EditText) findViewById(R.id.title_text);
        EditText contactText = (EditText) findViewById(R.id.contact_text);
        EditText dateText = (EditText) findViewById(R.id.dateText);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionText);
        Spinner societySpinner = (Spinner) findViewById(R.id.societies_spinner);
        Spinner typeSpinner = (Spinner) findViewById(R.id.type_spinner);

        String title = titleText.getText().toString();
        String contact = contactText.getText().toString();
        String date = dateText.getText().toString();
        String description = descriptionText.getText().toString();
        String society = societySpinner.getSelectedItem().toString();
        String type = typeSpinner.getSelectedItem().toString();

        intent.putExtra("title", title);
        intent.putExtra("contact", contact);
        intent.putExtra("date", date);
        intent.putExtra("description", description);
        intent.putExtra("society", society);
        intent.putExtra("type", type);
        startActivity(intent);
    }


}
