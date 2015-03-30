package com.kaiser.aaa.myactionbarmenu.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.Utils.HttpURLConnHelper;
import com.kaiser.aaa.myactionbarmenu.Utils.JsonHelper;
import com.kaiser.aaa.myactionbarmenu.entity.SearchNode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class SearchActivity extends ActionBarActivity {

    private SearchView searchView;
    private ListView listView_SearchActivity_searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = ((SearchView) findViewById(R.id.searchView_SearchActivity));
        listView_SearchActivity_searchResult = ((ListView) findViewById(R.id.listView_SearchActivity_searchResult));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //GET请求提交数据，http://webapi.yilule.com:5580/api/TourData?count=100&keyword=%E4%B8%8A%E6%B5%B7
                try {
                    byte[] bytes = HttpURLConnHelper.loadByteFromURL("http://webapi.yilule.com:5580/api/TourData?count=100&keyword=" + URLEncoder.encode(s, "utf-8"));
                    List<SearchNode> list = JsonHelper.jsonArrayToList(new String(bytes));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return true;
            }
        });

    }
}
