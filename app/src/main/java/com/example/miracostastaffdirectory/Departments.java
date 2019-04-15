package com.example.miracostastaffdirectory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Departments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);




    }

    public void goHome(View v) {
        this.finish();
    }



}
