package com.andro.whatswebapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends Toast {
    private Context context;

    public CustomToast(Context context2) {
        super(context2);
        this.context = context2;
    }

    @Override
    public void setView(View view) {
        super.setView(view);
    }

    public void setCustomView(String str) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.custom_toast, (ViewGroup) null);
        }
        ((TextView) view.findViewById(R.id.toast_txtMessage)).setText(str);
        setView(view);
    }
}
