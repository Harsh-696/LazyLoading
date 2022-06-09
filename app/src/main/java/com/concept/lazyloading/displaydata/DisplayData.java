package com.concept.lazyloading.displaydata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.concept.lazyloading.R;
import com.concept.lazyloading.database.Bridge;
import com.concept.lazyloading.database.Data;
import com.concept.lazyloading.database.DatabaseHandler;

import java.util.List;

public class DisplayData extends AppCompatActivity{

    RecyclerView recyclerView;
    Bridge bridge;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    LinearLayoutManager layoutManager;

    List<Data> dataList;
    List<Data> temporaryList;

    int initial = 4;
    int position = 0;
    boolean isScrolling = false;
    int currentItem, totalItem, scrolledItems;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        progressBar = findViewById(R.id.buffering);
        progressBar.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.dataRecycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        fetchData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                progressBar.setVisibility(View.VISIBLE);

                currentItem = layoutManager.getChildCount();
                totalItem = layoutManager.getItemCount();
                scrolledItems = layoutManager.findFirstVisibleItemPosition();

                if (isScrolling && currentItem + scrolledItems == totalItem && position <= 71) {
                    isScrolling = false;

                    Log.e("SCROLLED", "DOWN");
                    position = position + initial;
                    insertItems();
                }

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void insertItems() {

        progressBar.setVisibility(View.GONE);
        if (position <= 71) {
            temporaryList = databaseHandler.getLimitedData(initial, position);
            dataList.addAll(temporaryList);
            bridge.notifyDataSetChanged();

            // Log
            Log.e("VALUE OF POSITION", String.valueOf(position));
            Log.e("SIZE of DATA LIST", String.valueOf(dataList.size()));
            Log.e("SIZE OF TEMPORARY LIST", String.valueOf(temporaryList.size()));
        }
        else {
            Toast.makeText(getApplicationContext(), "All Data is Fetched", Toast.LENGTH_SHORT).show();
        }

    }

    private void fetchData() {
        dataList = databaseHandler.getLimitedData(initial, position);
        bridge = new Bridge(this, dataList);
        recyclerView.setAdapter(bridge);
    }

}