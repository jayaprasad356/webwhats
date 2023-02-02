package com.andro.whatswebapp;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ErrorView extends LinearLayout {
    public static final int ALIGNMENT_CENTER = 1;
    public static final int ALIGNMENT_LEFT = 0;
    public static final int ALIGNMENT_RIGHT = 2;
    private ImageView mErrorImageView;

    public RetryListener mListener;
    private Drawable mPrev;
    private TextView mRetryButton;
    private TextView mSubtitleTextView;
    private TextView mTitleTextView;

    public interface RetryListener {
        void onRetry();
    }

    public ErrorView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ErrorView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.ev_style);
    }

    public ErrorView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ErrorView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet);
        init(attributeSet, i, i2);
    }

    private void init(AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.ErrorView, i, i2);
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.errorview_layout, this, true);
        setOrientation(1);
        setGravity(17);
        if (Build.VERSION.SDK_INT >= 11) {
            setLayoutTransition(new LayoutTransition());
        }
        this.mErrorImageView = (ImageView) findViewById(R.id.error_image);
        this.mTitleTextView = (TextView) findViewById(R.id.error_title);
        this.mSubtitleTextView = (TextView) findViewById(R.id.error_subtitle);
        this.mRetryButton = (TextView) findViewById(R.id.error_retry);
        try {
            int resourceId = obtainStyledAttributes.getResourceId(0, R.drawable.logo);
            String string = obtainStyledAttributes.getString(10);
            int color = obtainStyledAttributes.getColor(11, getResources().getColor(R.color.error_view_text));
            String string2 = obtainStyledAttributes.getString(7);
            int color2 = obtainStyledAttributes.getColor(9, getResources().getColor(R.color.error_view_text_light));
            boolean z = obtainStyledAttributes.getBoolean(6, true);
            boolean z2 = obtainStyledAttributes.getBoolean(5, true);
            boolean z3 = obtainStyledAttributes.getBoolean(4, true);
            String string3 = obtainStyledAttributes.getString(2);
            int resourceId2 = obtainStyledAttributes.getResourceId(1, R.drawable.errorviewretrybtnbg);
            int color3 = obtainStyledAttributes.getColor(3, getResources().getColor(R.color.error_view_text_dark));
            int i3 = obtainStyledAttributes.getInt(8, 1);
            if (resourceId != 0) {
                setImage(resourceId);
            }
            if (string != null) {
                setTitle(string);
            }
            if (string2 != null) {
                setSubtitle(string2);
            }
            if (string3 != null) {
                this.mRetryButton.setText(string3);
            }
            if (!z) {
                this.mTitleTextView.setVisibility(View.GONE);
            }
            if (!z2) {
                this.mSubtitleTextView.setVisibility(View.GONE);
            }
            if (!z3) {
                this.mRetryButton.setVisibility(View.GONE);
            }
            this.mTitleTextView.setTextColor(color);
            this.mSubtitleTextView.setTextColor(color2);
            this.mRetryButton.setTextColor(color3);
            this.mRetryButton.setBackgroundResource(resourceId2);
            setSubtitleAlignment(i3);
            obtainStyledAttributes.recycle();
            this.mRetryButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (ErrorView.this.mListener != null) {
                        ErrorView.this.mListener.onRetry();
                    }
                }
            });
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void setImageVisibility(int i) {
        this.mErrorImageView.setVisibility(i);
    }

    public void setOnRetryListener(RetryListener retryListener) {
        this.mListener = retryListener;
    }

    public void showRetryButton(boolean z) {
        this.mRetryButton.setVisibility(z ? 0 : 8);
    }

    public void setRetryButtonText(String str) {
        this.mRetryButton.setText(str);
    }

    public Drawable getImage() {
        return this.mErrorImageView.getDrawable();
    }

    public void setImage(int i) {
        this.mErrorImageView.setImageResource(i);
    }

    public void setImage(Drawable drawable) {
        this.mErrorImageView.setImageDrawable(drawable);
    }

    public void setImage(Bitmap bitmap) {
        this.mErrorImageView.setImageBitmap(bitmap);
    }

    public String getTitle() {
        return this.mTitleTextView.getText().toString();
    }

    public void setTitle(String str) {
        this.mTitleTextView.setText(str);
    }

    public void setTitle(int i) {
        this.mTitleTextView.setText(i);
    }

    public String getSubtitle() {
        return this.mSubtitleTextView.getText().toString();
    }

    public void setSubtitle(String str) {
        this.mSubtitleTextView.setText(str);
    }

    public void setSubtitle(int i) {
        this.mSubtitleTextView.setText(i);
    }

    public void setRetryButtonText(int i) {
        this.mRetryButton.setText(i);
    }
/**      * @deprecated (when, why, refactoring advice...)      */
    @Deprecated
    public void setImageResource(int i) {
        setImage(i);
    }
/**      * @deprecated (when, why, refactoring advice...)      */
    @Deprecated
    public void setImageDrawable(Drawable drawable) {
        setImage(drawable);
    }

    /**      * @deprecated (when, why, refactoring advice...)      */
    @Deprecated
    public void setImageBitmap(Bitmap bitmap) {
        setImage(bitmap);
    }

    public void setSubtitleAlignment(int i) {
        if (i == 0) {
            this.mSubtitleTextView.setGravity(3);
        } else if (i == 1) {
            this.mSubtitleTextView.setGravity(1);
        } else {
            this.mSubtitleTextView.setGravity(5);
        }
    }

    @Override
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 8) {
            this.mPrev = ((View) getParent()).getBackground();
            ((View) getParent()).setBackgroundColor(-1);
        } else if (this.mPrev != null) {
            ((View) getParent()).setBackground(this.mPrev);
        }
    }
}
