package com.stylingandroid.smoothscrolling;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements Runnable {

    private RecyclerView recyclerView;
    private Handler handler;
    private int totalItem;
    private int targetPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(LargeAdapter.newInstance(this));
        int duration = getResources().getInteger(R.integer.scroll_duration);
        recyclerView.setLayoutManager(new ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, duration));
        FastScroller fastScroller = (FastScroller) findViewById(R.id.fastscroller);
        fastScroller.setRecyclerView(recyclerView);

        handler = new Handler();
    }

    @Override
    public void run() {
        targetPos = targetPos - 80;
        recyclerView.scrollToPosition(targetPos);
        if (targetPos > 0) {
            handler.postDelayed(this, 5);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_top) {
            totalItem = recyclerView.getAdapter().getItemCount();
            targetPos = totalItem;
            handler.postDelayed(this, 50);
            return true;
        } else if (id == R.id.action_bottom) {
            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
