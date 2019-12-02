package com.example.se_team5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se_team5.item.AllItems;
import com.example.se_team5.item.Item;
import com.example.se_team5.item.ItemsAdapter;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class RecommendActivity extends AppCompatActivity {

    private static ArrayList<Item> SELECTED_ITEMS1 = new ArrayList<Item>();
    private static ArrayList<Item> SELECTED_ITEMS2 = new ArrayList<Item>();
    private RecyclerView recyclerView;
    private ItemsAdapter myAdapter;

    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);

        Button PutButton = findViewById(R.id.putButton);

        recyclerView = findViewById(R.id.putRecyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록

        myAdapter = new ItemsAdapter(new AllItems().getAllItem());
        recyclerView.setAdapter(myAdapter);  // Adapter 등록
        PutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new recommendRecipe(RecommendActivity.this).execute("/recipe/refrigerator", "{\"good\":[3,4],\"bad\":[1,2]}");
            }
        });
    }

    private static class recommendRecipe extends AsyncTask<String, Void, String> {

        private WeakReference<RecommendActivity> activityReference;
            recommendRecipe(RecommendActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... params) {

            String success;

            HttpURLConnection httpURLConnection = null;
            try {
                HttpRequest req = new HttpRequest(MyGlobal.getData());
                return req.sendPost(params[0], params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // server와의 connection 해제
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return null;

        }
        protected void onPostExecute(String success) {
            super.onPostExecute(success);

            RecommendActivity activity = activityReference.get();

            if (activity == null) return;
            if(success == null) return ;

            if (success.substring(0,3).equals("200")) {
                //성공 시, recommend recipe 화면 띄우기
                //Intent intent = new Intent(activity, MainActivity.class);
                //activity.startActivity(intent);
            } else {

            }
        }
    }
}