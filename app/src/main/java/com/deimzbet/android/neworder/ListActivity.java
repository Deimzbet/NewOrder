package com.deimzbet.android.neworder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentList);

        if (fragment == null) {
            fragment = new ListActivityFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentList, fragment)
                    .commit();
        }
    }

}
