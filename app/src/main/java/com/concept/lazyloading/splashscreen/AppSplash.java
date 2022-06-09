package com.concept.lazyloading.splashscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.concept.lazyloading.MainActivity;

public class AppSplash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(AppSplash.this, MainActivity.class));
        finish();
    }
}
