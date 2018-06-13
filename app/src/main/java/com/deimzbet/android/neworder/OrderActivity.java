package com.deimzbet.android.neworder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

public class OrderActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "com.deimzbet.android.neworder.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        Intent data = getIntent();
        UUID id = (UUID) data.getSerializableExtra(EXTRA_ID);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentOrder);

        if ( fragment == null) {
            fragment = OrderActivityFragment.newInstance(id);
            fm.beginTransaction()
                    .add(R.id.fragmentOrder, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static Intent createIntent(Context context, UUID id) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }
}
