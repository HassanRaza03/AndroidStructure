package com.android.structure.libraries.table.util;

import com.android.structure.BaseApplication;

import com.android.structure.R;

public class ConstVar {

    public final static int MAXROWCOUNT = 32768;
    public final static int MAXCOLUMNCOUNT = 256;

//    public final static int HEADERWIDTH = 475;
//    public final static int HEADERHEIGHT = 95;

    public final static int HEADERWIDTH = (int) BaseApplication.getContext().getResources().getDimension(R.dimen.x140dp);
    public final static int HEADERHEIGHT = (int) BaseApplication.getContext().getResources().getDimension(R.dimen.x40dp);
    public final static int DEFAULTTEXTSIZE = (int) BaseApplication.getContext().getResources().getDimension(R.dimen.s10);
    public final static int HEADER_LEFT_PADDING = (int) BaseApplication.getContext().getResources().getDimension(R.dimen.x3dp);

//    public final static int DEFAULTTEXTSIZE = 35;
//    public final static int HEADER_LEFT_PADDING = 15;

    public final static int DRAWCELL_BG = 0x1;
    public final static int DRAWCELL_BORDER = 0x2;
    public final static int DRAWCELL_TEXT = 0x4;
    public final static int DRAWCELL_OBJECT = 0x8;
    public final static int DRAWCELL_ALL = DRAWCELL_BG | DRAWCELL_BORDER | DRAWCELL_TEXT | DRAWCELL_OBJECT;

    public final static int ZOOMINMAX = 150;
    public final static int ZOOMOUTMIN = 10;
    public final static int ZOOMSTEP = 15;
    public final static int FITWIDTH = -1;
    public final static int FITHEIGHT = -2;


    public final static int HIT_NONE = 0;
    public final static int HIT_RCHEADER = 1;
    public final static int HIT_ROWHEADER = 2;
    public final static int HIT_COLUMNHEADER = 3;
    public final static int HIT_ROWHEADER_RESIZE = 4;
    public final static int HIT_COLUMNHEADER_RESIZE = 5;
    public final static int HIT_TABLE = 6;
    public final static int HIT_SELECTION = 7;

//	public final static int SELECT_NONE = 0;
//	public final static int SELECT_LEFT2RIGHT = 0x1;
//	public final static int SELECT_RIGHT2LEFT = 0x2;
//	public final static int SELECT_TOP2BOTTOM = 0x4;
//	public final static int SELECT_BOTTOM2TOP = 0x8;

    public final static int RESIZE_AREA = 30;
}
