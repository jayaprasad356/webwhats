package com.vedha.whatsstatussaver.activity.whatsappsticker;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseeActivity extends AppCompatActivity {
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
