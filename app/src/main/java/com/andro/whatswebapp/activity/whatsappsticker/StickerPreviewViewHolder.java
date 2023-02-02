package com.andro.whatswebapp.activity.whatsappsticker;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.andro.whatswebapp.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class StickerPreviewViewHolder extends RecyclerView.ViewHolder {
    public SimpleDraweeView stickerPreviewView;

    StickerPreviewViewHolder(View view) {
        super(view);
        this.stickerPreviewView = (SimpleDraweeView) view.findViewById(R.id.sticker_preview);
    }
}
