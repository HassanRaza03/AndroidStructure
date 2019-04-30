package com.android.structure.libraries.table.model.object;


import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.android.structure.libraries.table.model.ICellData;
import com.android.structure.libraries.table.model.style.Font;

public class TextObject extends CellObject{
    private CharSequence text;
    private int fontIndex;
    private Font font;
    private Layout layout;
    private TextPaint textPaint;

    public TextObject(ICellData cell) {
        super(cell);
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setSubpixelText(true);
    }

    @Override
    public int getWidth() {
        return getLayout().getWidth();
    }

    @Override
    public int getHeight() {
        return getLayout().getHeight();
    }

    @Override
    public void onDraw(Canvas canvas) {
        getLayout().draw(canvas);
    }

    @Override
    protected void onDrawableStateChanged(int[] state) {

    }

    public void setText(CharSequence text) {
        this.text = text;
    }

    public CharSequence getText() {
        return text;
    }

    public int getFontIndex() {
        return fontIndex;
    }

    public void setFontIndex(int fontIndex) {
        this.fontIndex = fontIndex;
        font = getCell().getSheet().getFontManager().getFont(fontIndex);
        updateTextPaint();
    }

    private void updateTextPaint() {
        textPaint.setColor(font.getColor());
        textPaint.setTextSize(font.getFontSize());
    }

    private Layout getLayout() {
        if(layout == null) {
            int desireW = Math.round(Layout.getDesiredWidth(text, textPaint));
            Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
            layout = new StaticLayout(text, textPaint, desireW, alignment, 1.0f, 0.0f, false);
        }

        return layout;
    }
}
