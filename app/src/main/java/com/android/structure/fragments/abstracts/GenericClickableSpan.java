package com.android.structure.fragments.abstracts;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.android.structure.activities.BaseActivity;
import com.android.structure.callbacks.GenericClickableInterface;

import static android.graphics.Typeface.BOLD;

/**
 * Created by khanhamza on 18-Feb-17.
 */

public class GenericClickableSpan extends ClickableSpan {


    private TextView textView;
    private String clickableText;
    private SpannableString spannableString;
    private String fullTextViewString;
    private GenericClickableInterface genericClickableInterface;
    private BaseActivity baseActivity;
    private Boolean isUnderline = true;

    public void setUnderline(Boolean underline) {
        isUnderline = underline;
    }


    // For Future use

//    public GenericClickableSpan(TextView textView, String clickableText, GenericClickableInterface genericClickableInterface){
//
//        this.genericClickableInterface = genericClickableInterface;
//        this.textView = textView;
//        this.clickableText = clickableText;
//    }


    public GenericClickableSpan(BaseActivity activity, GenericClickableInterface genericClickableInterface) {
        this.genericClickableInterface = genericClickableInterface;
        this.baseActivity = activity;
    }

    public void setTextViewWithColor(int color) {
        textView.setLinkTextColor(color);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public void onClick(View widget) {
        genericClickableInterface.click();
    }


    public void setSpannableStringValue(TextView textView, String clickableText, SpannableString fullString) {
        this.textView = textView;
        this.clickableText = clickableText;
        this.fullTextViewString = textView.getText().toString().trim();
        this.spannableString = fullString;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
//        ds.setColor(baseActivity.getResources().getColor(R.color.offwhite));
        ds.setUnderlineText(isUnderline);
    }

    public void setSpan() {
        if (fullTextViewString.contains(clickableText)) {
            int endIndex = fullTextViewString.indexOf(clickableText) + clickableText.length();
            int startIndex = fullTextViewString.lastIndexOf(clickableText);
            spannableString.setSpan(this, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(1f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }


    public void setSpan(float size) {
        if (fullTextViewString.contains(clickableText)) {
            int endIndex = fullTextViewString.indexOf(clickableText) + clickableText.length();
            int startIndex = fullTextViewString.lastIndexOf(clickableText);
            spannableString.setSpan(this, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(size), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public void setTextView() {
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        textView.setHighlightColor(Color.BLUE);
    }


    public SpannableString getSpannableString() {
        return spannableString;
    }
}
