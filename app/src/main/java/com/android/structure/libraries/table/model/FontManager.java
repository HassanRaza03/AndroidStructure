package com.android.structure.libraries.table.model;


import com.android.structure.libraries.table.model.style.Font;

import java.util.ArrayList;
import java.util.List;

public class FontManager {
//    private static FontManager instance;

    private List<Font> fonts;

//    public static FontManager getInstance() {
//        if(instance == null) {
//            instance = new FontManager();
//        }
//        return instance;
//    }

    public FontManager() {
        fonts = new ArrayList<>();
    }

    public int addFont(Font font) {
        int index = -1;
        for(int i = 0; i < fonts.size(); i++) {
            Font f = fonts.get(i);
            if(f.equals(font)) {
                index = i;
                break;
            }
        }
        if(index != -1) {
            return index;
        } else {
            fonts.add(font);
            return fonts.size()-1;
        }
    }

    public Font getFont(int index) {
        Font font = null;
        try {
            font = fonts.get(index);
        } catch (IndexOutOfBoundsException e) {

        }

        return font;
    }
}
