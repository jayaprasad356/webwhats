package com.andro.whatswebapp.extras;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;

import androidx.core.content.ContextCompat;

import com.andro.whatswebapp.R;

public class SwitchButton extends CompoundButton {
    private static final int[] CHECKED_PRESSED_STATE = {16842912, 16842910, 16842919};
    public static final int DEFAULT_TINT_COLOR = 3309506;
    private static final int[] UNCHECKED_PRESSED_STATE = {-16842912, 16842910, 16842919};
    private long mAnimationDuration;
    private boolean mAutoAdjustTextPosition = true;
    private ColorStateList mBackColor;
    private Drawable mBackDrawable;
    private float mBackMeasureRatio;
    private float mBackRadius;
    private RectF mBackRectF;
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private int mClickTimeout;
    private int mCurrBackColor;
    private int mCurrThumbColor;
    private Drawable mCurrentBackDrawable;
    private boolean mDrawDebugRect = false;
    private boolean mFadeBack;
    private boolean mIsBackUseDrawable;
    private boolean mIsThumbUseDrawable;
    private float mLastX;
    private int mNextBackColor;
    private Drawable mNextBackDrawable;
    private Layout mOffLayout;
    private int mOffTextColor;
    private Layout mOnLayout;
    private int mOnTextColor;
    private Paint mPaint;
    private RectF mPresentThumbRectF;
    private float mProcess;
    private ObjectAnimator mProcessAnimator;
    private Paint mRectPaint;
    private RectF mSafeRectF;
    private float mStartX;
    private float mStartY;
    private float mTextHeight;
    private float mTextMarginH;
    private CharSequence mTextOff;
    private RectF mTextOffRectF;
    private CharSequence mTextOn;
    private RectF mTextOnRectF;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private ColorStateList mThumbColor;
    private Drawable mThumbDrawable;
    private RectF mThumbMargin;
    private float mThumbRadius;
    private RectF mThumbRectF;
    private PointF mThumbSizeF;
    private int mTintColor;
    private int mTouchSlop;

    public SwitchButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public SwitchButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public SwitchButton(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    private void init(AttributeSet attributeSet) {
        boolean z;
        int i;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        ColorStateList colorStateList;
        Drawable drawable;
        float f6;
        float f7;
        float f8;
        float f9;
        ColorStateList colorStateList2;
        Drawable drawable2;
        int i2;
        String str;
        String str2;
        boolean z2;
        float f10;
        float f11;
        TypedArray typedArray;
        float f12;
        boolean z3;
        AttributeSet attributeSet2 = attributeSet;
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mPaint = new Paint(1);
        this.mRectPaint = new Paint(1);
        this.mRectPaint.setStyle(Paint.Style.STROKE);
        this.mRectPaint.setStrokeWidth(getResources().getDisplayMetrics().density);
        this.mTextPaint = getPaint();
        this.mThumbRectF = new RectF();
        this.mBackRectF = new RectF();
        this.mSafeRectF = new RectF();
        this.mThumbSizeF = new PointF();
        this.mThumbMargin = new RectF();
        this.mTextOnRectF = new RectF();
        this.mTextOffRectF = new RectF();
        this.mProcessAnimator = ObjectAnimator.ofFloat(this, "process", new float[]{0.0f, 0.0f}).setDuration(250);
        this.mProcessAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mPresentThumbRectF = new RectF();
        float f13 = getResources().getDisplayMetrics().density;
        float f14 = f13 * 2.0f;
        float f15 = f13 * 20.0f;
        float f16 = f15 / 2.0f;
        TypedArray obtainStyledAttributes = attributeSet2 == null ? null : getContext().obtainStyledAttributes(attributeSet2, R.styleable.SwitchButton);
        if (obtainStyledAttributes != null) {
            drawable2 = obtainStyledAttributes.getDrawable(11);
            colorStateList = obtainStyledAttributes.getColorStateList(10);
            float dimension = obtainStyledAttributes.getDimension(13, f14);
            float dimension2 = obtainStyledAttributes.getDimension(15, dimension);
            f7 = obtainStyledAttributes.getDimension(16, dimension);
            float dimension3 = obtainStyledAttributes.getDimension(17, dimension);
            f8 = obtainStyledAttributes.getDimension(14, dimension);
            f5 = obtainStyledAttributes.getDimension(19, f15);
            float dimension4 = obtainStyledAttributes.getDimension(12, f15);
            float dimension5 = obtainStyledAttributes.getDimension(18, Math.min(f5, dimension4) / 2.0f);
            float dimension6 = obtainStyledAttributes.getDimension(5, dimension5 + f14);
            drawable = obtainStyledAttributes.getDrawable(3);
            ColorStateList colorStateList3 = obtainStyledAttributes.getColorStateList(2);
            float f17 = dimension3;
            float f18 = obtainStyledAttributes.getFloat(4, 1.8f);
            int integer = obtainStyledAttributes.getInteger(0, 250);
            boolean z4 = obtainStyledAttributes.getBoolean(6, true);
            int color = obtainStyledAttributes.getColor(20, 0);
            str2 = obtainStyledAttributes.getString(9);
            int i3 = color;
            String string = obtainStyledAttributes.getString(8);
            f14 = obtainStyledAttributes.getDimension(7, Math.max(f14, dimension6 / 2.0f));
            boolean z5 = obtainStyledAttributes.getBoolean(1, true);
            obtainStyledAttributes.recycle();
            f9 = dimension4;
            f = dimension6;
            f2 = dimension5;
            f3 = f18;
            z2 = z5;
            i = integer;
            f6 = f17;
            z = z4;
            i2 = i3;
            str = string;
            f4 = dimension2;
            colorStateList2 = colorStateList3;
        } else {
            f9 = f15;
            f5 = f9;
            f2 = f16;
            f = f2;
            z2 = true;
            str2 = null;
            str = null;
            i2 = 0;
            drawable2 = null;
            colorStateList2 = null;
            f8 = 0.0f;
            f7 = 0.0f;
            f6 = 0.0f;
            drawable = null;
            colorStateList = null;
            f4 = 0.0f;
            f3 = 1.8f;
            i = 250;
            z = true;
        }
        if (attributeSet2 == null) {
            f11 = f8;
            f10 = f7;
            typedArray = null;
        } else {
            f11 = f8;
            f10 = f7;
            typedArray = getContext().obtainStyledAttributes(attributeSet2, new int[]{16842970, 16842981});
        }
        if (typedArray != null) {
            f12 = f6;
            boolean z6 = typedArray.getBoolean(0, true);
            boolean z7 = typedArray.getBoolean(1, z6);
            setFocusable(z6);
            setClickable(z7);
            typedArray.recycle();
        } else {
            f12 = f6;
        }
        this.mTextOn = str2;
        this.mTextOff = str;
        this.mTextMarginH = f14;
        this.mAutoAdjustTextPosition = z2;
        this.mThumbDrawable = drawable2;
        this.mThumbColor = colorStateList;
        this.mIsThumbUseDrawable = this.mThumbDrawable != null;
        this.mTintColor = i2;
        if (this.mTintColor == 0) {
            TypedValue typedValue = new TypedValue();
            z3 = true;
            if (getContext().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true)) {
                this.mTintColor = typedValue.data;
            } else {
                this.mTintColor = DEFAULT_TINT_COLOR;
            }
        } else {
            z3 = true;
        }
        if (!this.mIsThumbUseDrawable && this.mThumbColor == null) {
            this.mThumbColor = ColorUtils.generateThumbColorWithTintColor(this.mTintColor);
            this.mCurrThumbColor = this.mThumbColor.getDefaultColor();
        }
        if (this.mIsThumbUseDrawable) {
            f5 = Math.max(f5, (float) this.mThumbDrawable.getMinimumWidth());
            f9 = Math.max(f9, (float) this.mThumbDrawable.getMinimumHeight());
        }
        this.mThumbSizeF.set(f5, f9);
        this.mBackDrawable = drawable;
        this.mBackColor = colorStateList2;
        if (this.mBackDrawable == null) {
            z3 = false;
        }
        this.mIsBackUseDrawable = z3;
        if (!this.mIsBackUseDrawable && this.mBackColor == null) {
            this.mBackColor = ColorUtils.generateBackColorWithTintColor(this.mTintColor);
            this.mCurrBackColor = this.mBackColor.getDefaultColor();
            this.mNextBackColor = this.mBackColor.getColorForState(CHECKED_PRESSED_STATE, this.mCurrBackColor);
        }
        this.mThumbMargin.set(f4, f12, f10, f11);
        if (this.mThumbMargin.width() >= 0.0f) {
            f3 = Math.max(f3, 1.0f);
        }
        this.mBackMeasureRatio = f3;
        this.mThumbRadius = f2;
        this.mBackRadius = f;
        this.mAnimationDuration = (long) i;
        this.mFadeBack = z;
        this.mProcessAnimator.setDuration(this.mAnimationDuration);
        if (isChecked()) {
            setProcess(1.0f);
        }
    }

    private Layout makeLayout(CharSequence charSequence) {
        TextPaint textPaint = this.mTextPaint;
        return new StaticLayout(charSequence, textPaint, (int) Math.ceil((double) Layout.getDesiredWidth(charSequence, textPaint)), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }


    @Override
    public void onMeasure(int i, int i2) {
        CharSequence charSequence;
        CharSequence charSequence2;
        if (this.mOnLayout == null && (charSequence2 = this.mTextOn) != null) {
            this.mOnLayout = makeLayout(charSequence2);
        }
        if (this.mOffLayout == null && (charSequence = this.mTextOff) != null) {
            this.mOffLayout = makeLayout(charSequence);
        }
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    private int measureWidth(int i) {
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        int ceil = ceil((double) (this.mThumbSizeF.x * this.mBackMeasureRatio));
        if (this.mIsBackUseDrawable) {
            ceil = Math.max(ceil, this.mBackDrawable.getMinimumWidth());
        }
        Layout layout = this.mOnLayout;
        float width = layout != null ? (float) layout.getWidth() : 0.0f;
        Layout layout2 = this.mOffLayout;
        float width2 = layout2 != null ? (float) layout2.getWidth() : 0.0f;
        if (width == 0.0f && width2 == 0.0f) {
            this.mTextWidth = 0.0f;
        } else {
            this.mTextWidth = Math.max(width, width2) + (this.mTextMarginH * 2.0f);
            float f = (float) ceil;
            float f2 = f - this.mThumbSizeF.x;
            float f3 = this.mTextWidth;
            if (f2 < f3) {
                ceil = (int) (f + (f3 - f2));
            }
        }
        int max = Math.max(ceil, ceil((double) (((float) ceil) + this.mThumbMargin.left + this.mThumbMargin.right)));
        int max2 = Math.max(Math.max(max, getPaddingLeft() + max + getPaddingRight()), getSuggestedMinimumWidth());
        if (mode == 1073741824) {
            return Math.max(max2, size);
        }
        return mode == Integer.MIN_VALUE ? Math.min(max2, size) : max2;
    }

    private int measureHeight(int i) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int ceil = ceil((double) Math.max(this.mThumbSizeF.y, this.mThumbSizeF.y + this.mThumbMargin.top + this.mThumbMargin.right));
        Layout layout = this.mOnLayout;
        float height = layout != null ? (float) layout.getHeight() : 0.0f;
        Layout layout2 = this.mOffLayout;
        float height2 = layout2 != null ? (float) layout2.getHeight() : 0.0f;
        if (height == 0.0f && height2 == 0.0f) {
            this.mTextHeight = 0.0f;
        } else {
            this.mTextHeight = Math.max(height, height2);
            ceil = ceil((double) Math.max((float) ceil, this.mTextHeight));
        }
        int max = Math.max(ceil, getSuggestedMinimumHeight());
        int max2 = Math.max(max, getPaddingTop() + max + getPaddingBottom());
        if (mode == 1073741824) {
            return Math.max(max2, size);
        }
        return mode == Integer.MIN_VALUE ? Math.min(max2, size) : max2;
    }


    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            setup();
        }
    }

    private int ceil(double d) {
        return (int) Math.ceil(d);
    }

    private void setup() {
        float f = 0.0f;
        float paddingTop = ((float) getPaddingTop()) + Math.max(0.0f, this.mThumbMargin.top);
        float paddingLeft = ((float) getPaddingLeft()) + Math.max(0.0f, this.mThumbMargin.left);
        if (!(this.mOnLayout == null || this.mOffLayout == null || this.mThumbMargin.top + this.mThumbMargin.bottom <= 0.0f)) {
            paddingTop += (((((float) ((getMeasuredHeight() - getPaddingBottom()) - getPaddingTop())) - this.mThumbSizeF.y) - this.mThumbMargin.top) - this.mThumbMargin.bottom) / 2.0f;
        }
        if (this.mIsThumbUseDrawable) {
            PointF pointF = this.mThumbSizeF;
            pointF.x = Math.max(pointF.x, (float) this.mThumbDrawable.getMinimumWidth());
            PointF pointF2 = this.mThumbSizeF;
            pointF2.y = Math.max(pointF2.y, (float) this.mThumbDrawable.getMinimumHeight());
        }
        this.mThumbRectF.set(paddingLeft, paddingTop, this.mThumbSizeF.x + paddingLeft, this.mThumbSizeF.y + paddingTop);
        float f2 = this.mThumbRectF.left - this.mThumbMargin.left;
        float min = Math.min(0.0f, ((Math.max(this.mThumbSizeF.x * this.mBackMeasureRatio, this.mThumbSizeF.x + this.mTextWidth) - this.mThumbRectF.width()) - this.mTextWidth) / 2.0f);
        float min2 = Math.min(0.0f, (((this.mThumbRectF.height() + this.mThumbMargin.top) + this.mThumbMargin.bottom) - this.mTextHeight) / 2.0f);
        this.mBackRectF.set(f2 + min, (this.mThumbRectF.top - this.mThumbMargin.top) + min2, (((f2 + this.mThumbMargin.left) + Math.max(this.mThumbSizeF.x * this.mBackMeasureRatio, this.mThumbSizeF.x + this.mTextWidth)) + this.mThumbMargin.right) - min, (this.mThumbRectF.bottom + this.mThumbMargin.bottom) - min2);
        this.mSafeRectF.set(this.mThumbRectF.left, 0.0f, (this.mBackRectF.right - this.mThumbMargin.right) - this.mThumbRectF.width(), 0.0f);
        this.mBackRadius = Math.min(Math.min(this.mBackRectF.width(), this.mBackRectF.height()) / 2.0f, this.mBackRadius);
        Drawable drawable = this.mBackDrawable;
        if (drawable != null) {
            drawable.setBounds((int) this.mBackRectF.left, (int) this.mBackRectF.top, ceil((double) this.mBackRectF.right), ceil((double) this.mBackRectF.bottom));
        }
        if (this.mOnLayout != null) {
            float width = this.mBackRectF.left + ((((this.mBackRectF.width() - this.mThumbRectF.width()) - this.mThumbMargin.right) - ((float) this.mOnLayout.getWidth())) / 2.0f) + (this.mThumbMargin.left < 0.0f ? this.mThumbMargin.left * -0.5f : 0.0f);
            if (!this.mIsBackUseDrawable && this.mAutoAdjustTextPosition) {
                width += this.mBackRadius / 4.0f;
            }
            float height = this.mBackRectF.top + ((this.mBackRectF.height() - ((float) this.mOnLayout.getHeight())) / 2.0f);
            this.mTextOnRectF.set(width, height, ((float) this.mOnLayout.getWidth()) + width, ((float) this.mOnLayout.getHeight()) + height);
        }
        if (this.mOffLayout != null) {
            float width2 = (this.mBackRectF.right - ((((this.mBackRectF.width() - this.mThumbRectF.width()) - this.mThumbMargin.left) - ((float) this.mOffLayout.getWidth())) / 2.0f)) - ((float) this.mOffLayout.getWidth());
            if (this.mThumbMargin.right < 0.0f) {
                f = this.mThumbMargin.right * 0.5f;
            }
            float f3 = width2 + f;
            if (!this.mIsBackUseDrawable && this.mAutoAdjustTextPosition) {
                f3 -= this.mBackRadius / 4.0f;
            }
            float height2 = this.mBackRectF.top + ((this.mBackRectF.height() - ((float) this.mOffLayout.getHeight())) / 2.0f);
            this.mTextOffRectF.set(f3, height2, ((float) this.mOffLayout.getWidth()) + f3, ((float) this.mOffLayout.getHeight()) + height2);
        }
    }


    @Override
    public void onDraw(Canvas canvas) {
        float f;
        float process = 0;
        super.onDraw(canvas);
        float f2 = 1.0f;
        if (this.mIsBackUseDrawable) {
            if (!this.mFadeBack || this.mCurrentBackDrawable == null || this.mNextBackDrawable == null) {
                this.mBackDrawable.setAlpha(255);
                this.mBackDrawable.draw(canvas);
            } else {
                int process2 = (int) ((isChecked() ? getProcess() : 1.0f - getProcess()) * 255.0f);
                this.mCurrentBackDrawable.setAlpha(process2);
                this.mCurrentBackDrawable.draw(canvas);
                this.mNextBackDrawable.setAlpha(255 - process2);
                this.mNextBackDrawable.draw(canvas);
            }
        } else if (this.mFadeBack) {
            int process3 = (int) ((isChecked() ? getProcess() : 1.0f - getProcess()) * 255.0f);
            this.mPaint.setARGB((Color.alpha(this.mCurrBackColor) * process3) / 255, Color.red(this.mCurrBackColor), Color.green(this.mCurrBackColor), Color.blue(this.mCurrBackColor));
            RectF rectF = this.mBackRectF;
            float f3 = this.mBackRadius;
            canvas.drawRoundRect(rectF, f3, f3, this.mPaint);
            this.mPaint.setARGB((Color.alpha(this.mNextBackColor) * (255 - process3)) / 255, Color.red(this.mNextBackColor), Color.green(this.mNextBackColor), Color.blue(this.mNextBackColor));
            RectF rectF2 = this.mBackRectF;
            float f4 = this.mBackRadius;
            canvas.drawRoundRect(rectF2, f4, f4, this.mPaint);
            this.mPaint.setAlpha(255);
        } else {
            this.mPaint.setColor(this.mCurrBackColor);
            RectF rectF3 = this.mBackRectF;
            float f5 = this.mBackRadius;
            canvas.drawRoundRect(rectF3, f5, f5, this.mPaint);
        }
        Layout layout = ((double) getProcess()) > 0.5d ? this.mOnLayout : this.mOffLayout;
        RectF rectF4 = ((double) getProcess()) > 0.5d ? this.mTextOnRectF : this.mTextOffRectF;
        if (!(layout == null || rectF4 == null)) {
            if (((double) getProcess()) >= 0.75d) {
                f2 = getProcess() * 4.0f;
                process = 3.0f;
            } else if (((double) getProcess()) < 0.25d) {
                process = getProcess() * 4.0f;
            } else {
                f = 0.0f;
                int i = (int) (f * 255.0f);
                int i2 = ((double) getProcess()) <= 0.5d ? this.mOnTextColor : this.mOffTextColor;
                layout.getPaint().setARGB((Color.alpha(i2) * i) / 255, Color.red(i2), Color.green(i2), Color.blue(i2));
                canvas.save();
                canvas.translate(rectF4.left, rectF4.top);
                layout.draw(canvas);
                canvas.restore();
            }
            canvas.save();
            canvas.translate(rectF4.left, rectF4.top);
            layout.draw(canvas);
            canvas.restore();
        }
        this.mPresentThumbRectF.set(this.mThumbRectF);
        this.mPresentThumbRectF.offset(this.mProcess * this.mSafeRectF.width(), 0.0f);
        if (this.mIsThumbUseDrawable) {
            this.mThumbDrawable.setBounds((int) this.mPresentThumbRectF.left, (int) this.mPresentThumbRectF.top, ceil((double) this.mPresentThumbRectF.right), ceil((double) this.mPresentThumbRectF.bottom));
            this.mThumbDrawable.draw(canvas);
        } else {
            this.mPaint.setColor(this.mCurrThumbColor);
            RectF rectF5 = this.mPresentThumbRectF;
            float f6 = this.mThumbRadius;
            canvas.drawRoundRect(rectF5, f6, f6, this.mPaint);
        }
        if (this.mDrawDebugRect) {
            this.mRectPaint.setColor(Color.parseColor("#AA0000"));
            canvas.drawRect(this.mBackRectF, this.mRectPaint);
            this.mRectPaint.setColor(Color.parseColor("#0000FF"));
            canvas.drawRect(this.mPresentThumbRectF, this.mRectPaint);
            this.mRectPaint.setColor(Color.parseColor("#00CC00"));
            canvas.drawRect(((double) getProcess()) > 0.5d ? this.mTextOnRectF : this.mTextOffRectF, this.mRectPaint);
        }
    }


    @Override
    public void drawableStateChanged() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        super.drawableStateChanged();
        if (this.mIsThumbUseDrawable || (colorStateList2 = this.mThumbColor) == null) {
            setDrawableState(this.mThumbDrawable);
        } else {
            this.mCurrThumbColor = colorStateList2.getColorForState(getDrawableState(), this.mCurrThumbColor);
        }
        int[] iArr = isChecked() ? UNCHECKED_PRESSED_STATE : CHECKED_PRESSED_STATE;
        ColorStateList textColors = getTextColors();
        if (textColors != null) {
            int defaultColor = textColors.getDefaultColor();
            this.mOnTextColor = textColors.getColorForState(CHECKED_PRESSED_STATE, defaultColor);
            this.mOffTextColor = textColors.getColorForState(UNCHECKED_PRESSED_STATE, defaultColor);
        }
        if (this.mIsBackUseDrawable || (colorStateList = this.mBackColor) == null) {
            Drawable drawable = this.mBackDrawable;
            if (!(drawable instanceof StateListDrawable) || !this.mFadeBack) {
                this.mNextBackDrawable = null;
            } else {
                drawable.setState(iArr);
                this.mNextBackDrawable = this.mBackDrawable.getCurrent().mutate();
            }
            setDrawableState(this.mBackDrawable);
            Drawable drawable2 = this.mBackDrawable;
            if (drawable2 != null) {
                this.mCurrentBackDrawable = drawable2.getCurrent().mutate();
                return;
            }
            return;
        }
        this.mCurrBackColor = colorStateList.getColorForState(getDrawableState(), this.mCurrBackColor);
        this.mNextBackColor = this.mBackColor.getColorForState(iArr, this.mCurrBackColor);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || !isClickable() || !isFocusable()) {
            return false;
        }
        int action = motionEvent.getAction();
        float x = motionEvent.getX() - this.mStartX;
        float y = motionEvent.getY() - this.mStartY;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    float x2 = motionEvent.getX();
                    setProcess(getProcess() + ((x2 - this.mLastX) / this.mSafeRectF.width()));
                    this.mLastX = x2;
                }
            }
            setPressed(false);
            boolean statusBasedOnPos = getStatusBasedOnPos();
            float eventTime = (float) (motionEvent.getEventTime() - motionEvent.getDownTime());
            int i = this.mTouchSlop;
            if (x < ((float) i) && y < ((float) i) && eventTime < ((float) this.mClickTimeout)) {
                performClick();
            } else if (statusBasedOnPos != isChecked()) {
                playSoundEffect(0);
                setChecked(statusBasedOnPos);
            } else {
                animateToState(statusBasedOnPos);
            }
        } else {
            catchView();
            this.mStartX = motionEvent.getX();
            this.mStartY = motionEvent.getY();
            this.mLastX = this.mStartX;
            setPressed(true);
        }
        return true;
    }

    private boolean getStatusBasedOnPos() {
        return getProcess() > 0.5f;
    }

    public final float getProcess() {
        return this.mProcess;
    }

    public final void setProcess(float f) {
        if (f > 1.0f) {
            f = 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        this.mProcess = f;
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    public void animateToState(boolean z) {
        ObjectAnimator objectAnimator = this.mProcessAnimator;
        if (objectAnimator != null) {
            if (objectAnimator.isRunning()) {
                this.mProcessAnimator.cancel();
            }
            this.mProcessAnimator.setDuration(this.mAnimationDuration);
            if (z) {
                this.mProcessAnimator.setFloatValues(new float[]{this.mProcess, 1.0f});
            } else {
                this.mProcessAnimator.setFloatValues(new float[]{this.mProcess, 0.0f});
            }
            this.mProcessAnimator.start();
        }
    }

    private void catchView() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override
    public void setChecked(boolean z) {
        if (isChecked() != z) {
            animateToState(z);
        }
        super.setChecked(z);
    }

    public void setCheckedNoEvent(boolean z) {
        if (this.mChildOnCheckedChangeListener == null) {
            setChecked(z);
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        setChecked(z);
        setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void setCheckedImmediatelyNoEvent(boolean z) {
        if (this.mChildOnCheckedChangeListener == null) {
            setCheckedImmediately(z);
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        setCheckedImmediately(z);
        setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void toggleNoEvent() {
        if (this.mChildOnCheckedChangeListener == null) {
            toggle();
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        toggle();
        setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void toggleImmediatelyNoEvent() {
        if (this.mChildOnCheckedChangeListener == null) {
            toggleImmediately();
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        toggleImmediately();
        setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    @Override
    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        super.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mChildOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setCheckedImmediately(boolean z) {
        super.setChecked(z);
        ObjectAnimator objectAnimator = this.mProcessAnimator;
        if (objectAnimator != null && objectAnimator.isRunning()) {
            this.mProcessAnimator.cancel();
        }
        setProcess(z ? 1.0f : 0.0f);
        invalidate();
    }

    public void toggleImmediately() {
        setCheckedImmediately(!isChecked());
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(getDrawableState());
            invalidate();
        }
    }

    public boolean isDrawDebugRect() {
        return this.mDrawDebugRect;
    }

    public void setDrawDebugRect(boolean z) {
        this.mDrawDebugRect = z;
        invalidate();
    }

    public long getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public void setAnimationDuration(long j) {
        this.mAnimationDuration = j;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public void setThumbDrawable(Drawable drawable) {
        this.mThumbDrawable = drawable;
        this.mIsThumbUseDrawable = this.mThumbDrawable != null;
        setup();
        refreshDrawableState();
        requestLayout();
        invalidate();
    }

    public void setThumbDrawableRes(int i) {
        setThumbDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public Drawable getBackDrawable() {
        return this.mBackDrawable;
    }

    public void setBackDrawable(Drawable drawable) {
        this.mBackDrawable = drawable;
        this.mIsBackUseDrawable = this.mBackDrawable != null;
        setup();
        refreshDrawableState();
        requestLayout();
        invalidate();
    }

    public void setBackDrawableRes(int i) {
        setBackDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public ColorStateList getBackColor() {
        return this.mBackColor;
    }

    public void setBackColor(ColorStateList colorStateList) {
        this.mBackColor = colorStateList;
        if (this.mBackColor != null) {
            setBackDrawable((Drawable) null);
        }
        invalidate();
    }

    public void setBackColorRes(int i) {
        setBackColor(ContextCompat.getColorStateList(getContext(), i));
    }

    public ColorStateList getThumbColor() {
        return this.mThumbColor;
    }

    public void setThumbColor(ColorStateList colorStateList) {
        this.mThumbColor = colorStateList;
        if (this.mThumbColor != null) {
            setThumbDrawable((Drawable) null);
        }
    }

    public void setThumbColorRes(int i) {
        setThumbColor(ContextCompat.getColorStateList(getContext(), i));
    }

    public float getBackMeasureRatio() {
        return this.mBackMeasureRatio;
    }

    public void setBackMeasureRatio(float f) {
        this.mBackMeasureRatio = f;
        requestLayout();
    }

    public RectF getThumbMargin() {
        return this.mThumbMargin;
    }

    public void setThumbMargin(RectF rectF) {
        if (rectF == null) {
            setThumbMargin(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            setThumbMargin(rectF.left, rectF.top, rectF.right, rectF.bottom);
        }
    }

    public void setThumbMargin(float f, float f2, float f3, float f4) {
        this.mThumbMargin.set(f, f2, f3, f4);
        requestLayout();
    }

    public void setThumbSize(float f, float f2) {
        this.mThumbSizeF.set(f, f2);
        setup();
        requestLayout();
    }

    public float getThumbWidth() {
        return this.mThumbSizeF.x;
    }

    public float getThumbHeight() {
        return this.mThumbSizeF.y;
    }

    public void setThumbSize(PointF pointF) {
        if (pointF == null) {
            float f = getResources().getDisplayMetrics().density * 20.0f;
            setThumbSize(f, f);
            return;
        }
        setThumbSize(pointF.x, pointF.y);
    }

    public PointF getThumbSizeF() {
        return this.mThumbSizeF;
    }

    public float getThumbRadius() {
        return this.mThumbRadius;
    }

    public void setThumbRadius(float f) {
        this.mThumbRadius = f;
        if (!this.mIsThumbUseDrawable) {
            invalidate();
        }
    }

    public PointF getBackSizeF() {
        return new PointF(this.mBackRectF.width(), this.mBackRectF.height());
    }

    public float getBackRadius() {
        return this.mBackRadius;
    }

    public void setBackRadius(float f) {
        this.mBackRadius = f;
        if (!this.mIsBackUseDrawable) {
            invalidate();
        }
    }

    public boolean isFadeBack() {
        return this.mFadeBack;
    }

    public void setFadeBack(boolean z) {
        this.mFadeBack = z;
    }

    public int getTintColor() {
        return this.mTintColor;
    }

    public void setTintColor(int i) {
        this.mTintColor = i;
        this.mThumbColor = ColorUtils.generateThumbColorWithTintColor(this.mTintColor);
        this.mBackColor = ColorUtils.generateBackColorWithTintColor(this.mTintColor);
        this.mIsBackUseDrawable = false;
        this.mIsThumbUseDrawable = false;
        refreshDrawableState();
        invalidate();
    }

    public void setText(CharSequence charSequence, CharSequence charSequence2) {
        this.mTextOn = charSequence;
        this.mTextOff = charSequence2;
        this.mOnLayout = null;
        this.mOffLayout = null;
        requestLayout();
        invalidate();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.onText = this.mTextOn;
        savedState.offText = this.mTextOff;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        setText(savedState.onText, savedState.offText);
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        CharSequence offText;
        CharSequence onText;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.onText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.offText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.onText, parcel, i);
            TextUtils.writeToParcel(this.offText, parcel, i);
        }
    }
}
