package com.u1626889.warwickdrama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import android.util.Log;


public class CreatePost2Activity extends AppCompatActivity {

    public ArrayList<String> placeholder_tags;
    ArrayAdapter<String> arrayAdapter;
//    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post2);

        final ListView lv = (ListView) findViewById(R.id.tag_list);
        String placeholder_tags_string = "science,maths,computers,period,drama,lgbt";
        placeholder_tags = new ArrayList<String>(Arrays.asList(placeholder_tags_string.split(",")));

        Log.d("thing","is it null? "+placeholder_tags);
        // TURNS THE TAG STRING INTO A LIST AND ADD IT TO THE LISTVIEW

        // Create an ArrayAdapter from List
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, placeholder_tags);
        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);

        //            MAKING IT SO THAT WHEN YOU LONG CLICK A TAG IT IS DELETED FROM THE LIST
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {
                placeholder_tags.remove(position);
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });


    }

    public void postPostData(View view) {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String contact = intent.getStringExtra("contact");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String society = intent.getStringExtra("society");
        String type = intent.getStringExtra("type");

//        ListView lv = (ListView) findViewById(R.id.tag_list);
        ArrayList<String> tags = new ArrayList<String>();
        for (int i=0;i<arrayAdapter.getCount();i++){
            tags.add(arrayAdapter.getItem(i));
        }
        Log.d("thing","title are "+title);

//        intent = new Intent(this, MainActivity.class);

//        startActivity(intent);

        // Composes a reply intent and
        Intent replyIntent = new Intent();
        if (tags.size() == 0) {
            // TODO - Finish adding validation to the user input
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            int newId = View.generateViewId();
            replyIntent.putExtra("id",newId);
            replyIntent.putExtra("title", title);
            replyIntent.putExtra("contact", contact);
            replyIntent.putExtra("date", date);
            replyIntent.putExtra("description", description);
            replyIntent.putExtra("society", society);
            replyIntent.putExtra("type", type);
            replyIntent.putExtra("tags", tags);
            setResult(RESULT_OK, replyIntent);
            Log.d("thing", "Packing the CreatePost2Activity reply with data");
        }
        finish();



    }

    public void addTag(View view) {

        EditText tagText = findViewById(R.id.new_tag_text);
        String newTag = tagText.getText().toString();
        if(!TextUtils.isEmpty(newTag)) {
            placeholder_tags.add(newTag);
            Log.d("thing",""+placeholder_tags);
            arrayAdapter.notifyDataSetChanged();

            tagText.setText("");
        }
    }

}
