package com.android.structure.models;


import android.content.Context;

import com.android.structure.R;
import com.android.structure.libraries.table.model.DefaultCellData;
import com.android.structure.libraries.table.model.DefaultSheetData;
import com.android.structure.libraries.table.model.ISheetData;
import com.android.structure.libraries.table.model.Range;
import com.android.structure.libraries.table.model.RichText;
import com.android.structure.libraries.table.model.style.CellStyle;
import com.android.structure.libraries.table.model.style.Font;
import com.android.structure.libraries.table.model.style.TableConst;
import com.android.structure.libraries.table.util.ConstVar;

public class SheetTemplate1 {

    //    public static ISheetData get(final Context context, LstMicSpecParaResult lstMicSpecParaResult) {


    public static ISheetData get(final Context context, String[][] tableData) {
        DefaultSheetData sheet = new DefaultSheetData(context);

        int rowCount;
        int colCount;
        if (tableData[0] == null) {
            rowCount = 0;
            colCount = 0;
        } else {
            rowCount = tableData[0].length;
            colCount = tableData.length;
        }
        sheet.setMaxRowCount(rowCount);
        sheet.setMaxColumnCount(colCount);

//        sheet.setMaxColumnCount(lstMicSpecParaResult.getLstMicSpecOrganism().size());
//        sheet.setFreezedRowCount(1);


        Font whiteFont = Font.createDefault(context);
        whiteFont.setColor(context.getResources().getColor(R.color.c_white));
        whiteFont.setFontSize(ConstVar.DEFAULTTEXTSIZE);
        int whiteFontIndex = sheet.getFontManager().addFont(whiteFont);


        Font blackFont = Font.createDefault(context);
        blackFont.setColor(context.getResources().getColor(R.color.c_black));
        whiteFont.setFontSize(ConstVar.DEFAULTTEXTSIZE);
        int blackFontIndex = sheet.getFontManager().addFont(blackFont);

        CellStyle cellStyle = new CellStyle();
        cellStyle.setBgColor(context.getResources().getColor(R.color.c_white_smoke)); // odd cells color
        cellStyle.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyle.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        cellStyle.setFontIndex(blackFontIndex);
        int lightGreyStyle = sheet.getCellStyleManager().addCellStyle(cellStyle);

        CellStyle cellStyle1 = new CellStyle();
        cellStyle1.setBgColor(context.getResources().getColor(R.color.yellow_sensitive)); // odd cells color
        cellStyle1.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyle1.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        cellStyle1.setFontIndex(whiteFontIndex);
        int yellowStyle = sheet.getCellStyleManager().addCellStyle(cellStyle1);

        CellStyle cellStyle2 = new CellStyle();
        cellStyle2.setBgColor(context.getResources().getColor(R.color.green_intermediate)); // odd cells color
        cellStyle2.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyle2.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        cellStyle2.setFontIndex(whiteFontIndex);
        int greenStyle = sheet.getCellStyleManager().addCellStyle(cellStyle2);

        CellStyle cellStyle3 = new CellStyle();
        cellStyle3.setBgColor(context.getResources().getColor(R.color.red_resistant)); // odd cells color
        cellStyle3.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyle3.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        cellStyle3.setFontIndex(whiteFontIndex);
        int redStyle = sheet.getCellStyleManager().addCellStyle(cellStyle3);


        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                DefaultCellData cell = new DefaultCellData(sheet);
                RichText richText = new RichText();


                richText.setText(tableData[i][j]);

                if (tableData[i][j].contains("Resistant")) {
                    cell.setStyleIndex(redStyle);

                } else if (tableData[i][j].contains("Intermediate")) {
                    cell.setStyleIndex(yellowStyle);

                } else if (tableData[i][j].contains("Sensitive")) {
                    cell.setStyleIndex(greenStyle);
                } else {
                    cell.setStyleIndex(lightGreyStyle);
                }
                cell.setCellValue(richText);
                sheet.setCellData(cell, j, i);
             }
        }


        return sheet;
    }

    private static void addMergeRange(DefaultSheetData sheet) {
        Range range = new Range(0, 2, 1, 3);
        sheet.addMergedRange(range);
    }
}
