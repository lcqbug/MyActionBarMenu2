package com.kaiser.aaa.myactionbarmenu.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kaiser.aaa.myactionbarmenu.R;

import java.util.List;
import java.util.Map;

public class CommentActivity extends ActionBarActivity {
    private EditText editText_Comment;
    private Button button_Comment;
    private ListView listView_Comment;
    private List<Map<String,String>> list;
    private String comment="http://webapi.yilule.com:5580/api/Comments?pid=799&pageSize=20&pageIndex=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        listView_Comment= (ListView) findViewById(R.id.listView_Comment);
        editText_Comment= (EditText) findViewById(R.id.editText_Comment);
        button_Comment= (Button) findViewById(R.id.button_Comment);

        button_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),list,R.layout.comment_item,new String[]{},new int[]{});
        //listView_Comment.setAdapter();
    }



}
