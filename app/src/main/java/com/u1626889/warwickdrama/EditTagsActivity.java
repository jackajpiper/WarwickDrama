package com.u1626889.warwickdrama;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class EditTagsActivity extends AppCompatActivity {

    public ArrayList<String> placeholder_tags;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_tags);



        final ListView lv = findViewById(R.id.tag_list);

        SharedPreferences prefs = getSharedPreferences("user_tags",0);
        String tags = prefs.getString("user_tags","");
        placeholder_tags = new ArrayList<String>(Arrays.asList(tags.split(",")));


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


//        Log.d("thing", "theeeese tags are "+arrayAdapter.getItem(0)+", "+arrayAdapter.getItem(1));

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

    public void updateTags(View view) {

        ArrayList<String> tagsArr = new ArrayList<String>();
        for (int i=0;i<arrayAdapter.getCount();i++){
            tagsArr.add(arrayAdapter.getItem(i));
        }

        String tags = android.text.TextUtils.join(",", tagsArr);

        // Composes a reply intent and
        Intent replyIntent = new Intent();
        if (tagsArr.size() == 0) {
            // TODO - Finish adding validation to the user input
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            replyIntent.putExtra("new_tags", tags);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

}
