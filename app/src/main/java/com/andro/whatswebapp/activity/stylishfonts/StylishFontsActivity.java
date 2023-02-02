package com.andro.whatswebapp.activity.stylishfonts;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;
import com.andro.whatswebapp.activity.MainActivity;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class StylishFontsActivity extends AppCompatActivity {
    EditText etText;
    ArrayList<Font> fontList;
    String fontText;
    AdView llAds;
    RecyclerView rvStylishFonts;
    private TextView tvTitle;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_stylish_fonts_main);
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        this.llAds = (AdView) findViewById(R.id.ll_ads);
        Ads.bannerad(this.llAds, this);
        this.tvTitle = (TextView) findViewById(R.id.title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        this.tvTitle.setText("Stylish Font");
        ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StylishFontsActivity.this.startActivity(new Intent(StylishFontsActivity.this, MainActivity.class));
                StylishFontsActivity.this.finish();
            }
        });
        this.etText = (EditText) findViewById(R.id.et_text);
        this.rvStylishFonts = (RecyclerView) findViewById(R.id.rv_fonts);
        this.fontList = new ArrayList<>();
        this.etText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                StylishFontsActivity.this.makeStylishOf(charSequence);
            }
        });
    }


    public void makeStylishOf(CharSequence charSequence) {
        char[] charArray = charSequence.toString().toLowerCase().toCharArray();
        String[] strArr = new String[44];
        for (int i = 0; i < 44; i++) {
            strArr[i] = applyStyle(charArray, c.strings[i]);
        }
        styleTheFont(strArr);
    }

    private String applyStyle(char[] cArr, String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < cArr.length; i++) {
            if (cArr[i] - 'a' < 0 || cArr[i] - 'a' > 25) {
                stringBuffer.append(cArr[i]);
            } else {
                stringBuffer.append(strArr[cArr[i] - 'a']);
            }
        }
        return stringBuffer.toString();
    }

    private void styleTheFont(String[] strArr) {
        this.fontList.clear();
        this.fontText = this.etText.getText().toString().trim();
        if (!this.fontText.isEmpty()) {
            for (int i = 0; i < 44; i++) {
                Font font = new Font();
                font.fontText = strArr[i];
                this.fontList.add(font);
            }
            this.rvStylishFonts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            this.rvStylishFonts.setAdapter(new FontAdapter(this, this.fontList, new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                }
            }));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
