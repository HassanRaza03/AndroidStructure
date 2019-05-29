package com.android.structure.libraries.table.model;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.android.structure.libraries.table.model.style.CellStyle;
import com.android.structure.libraries.table.model.style.Font;
import com.android.structure.libraries.table.model.style.TableConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultSheetData extends BaseSheetData{
    private Context context;
    private int freezedRowCount, freezedColCount;
    private CellStyleManager cellStyleManager;
    private FontManager fontManager;
    private int styleId;
    private SparseIntArray rowHeights, colWidths;
    private SparseBooleanArray hiddenRows, hiddenColums;
    private int gridLineColor;
    private Map<Long, ICellData> cells;
    private List<Range> mergedRanges;
    private int maxRowCount, maxColCount;

    public DefaultSheetData(Context context) {
        gridLineColor = Color.GRAY;
        rowHeights = new SparseIntArray();
        colWidths = new SparseIntArray();
        hiddenRows = new SparseBooleanArray();
        hiddenColums = new SparseBooleanArray();
        cells = new HashMap<>();
        mergedRanges = new ArrayList<>();

        cellStyleManager = new CellStyleManager();
        fontManager = new FontManager();

        CellStyle sheetStyle = new CellStyle();
        sheetStyle.setAlignment(TableConst.ALIGNMENT_CENTER);
        sheetStyle.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        styleId = cellStyleManager.addCellStyle(sheetStyle);

        Font font = Font.createDefault(context);
        int fontId = fontManager.addFont(font);
        sheetStyle.setFontIndex(fontId);

    }

    @Override
    public boolean isFreeze() {
        return getFreezedRowCount() > 0 || getFreezedColCount() > 0;
    }

    @Override
    public int getFreezedRowCount() {
        return freezedRowCount;
    }

    public void setFreezedRowCount(int count) {
        freezedRowCount = count;
    }

    @Override
    public int getFreezedColCount() {
        return freezedColCount;
    }

    public void setFreezedColCount(int count) {
        freezedColCount = count;
    }

    @Override
    public int getRowHeight(int rowIndex) {
        return rowHeights.get(rowIndex);
    }

    @Override
    public void setRowHeight(int rowIndex, int rowHeight) {
        rowHeights.put(rowIndex, rowHeight);
    }

    @Override
    public int getColumnWidth(int colIndex) {
        return colWidths.get(colIndex);
    }

    @Override
    public void setColumnWidth(int colIndex, int columnWidth) {
        colWidths.put(colIndex, columnWidth);
        updateColumnCellData(colIndex);
    }

    public void updateColumnCellData(int colIndex) {
        for(int i = 0; i < getLastRow(); i++) {
            long cellPos = getCellPosition(i, colIndex);
            ICellData cellData = cells.get(cellPos);
            if(cellData != null) {
                cellData.update();
            }
        }
    }

    @Override
    public boolean isRowHidden(int rowIndex) {
        return hiddenRows.get(rowIndex);
    }

    public void setRowHidden(int rowIndex, boolean hidden) {
        hiddenRows.put(rowIndex, hidden);
    }

    @Override
    public boolean isColumnHidden(int colIndex) {
        return hiddenColums.get(colIndex);
    }

    public void setColumnHidden(int colIndex, boolean hidden) {
        hiddenColums.put(colIndex, hidden);
    }

    @Override
    public ICellData getCellData(int rowIndex, int colIndex) {
        long cellPos = getCellPosition(rowIndex, colIndex);
        return cells.get(cellPos);
    }

    public void setCellData(ICellData cell, int rowIndex, int colIndex) {
        long cellPos = getCellPosition(rowIndex, colIndex);
        cells.put(cellPos, cell);
        Range range = checkCellMerged(rowIndex, colIndex);
        cell.setMergedRange(range);
    }

    @Override
    public Range inMergedRange(int rowIndex, int colIndex, boolean includeFirstCell) {
        Range range = null;
        for(Range r : mergedRanges) {
            if(r.isInRange(rowIndex, colIndex)) {
                boolean bFirstCell = false;
                if(r.getTop() == rowIndex &&
                        r.getLeft() == colIndex) {
                    bFirstCell = true;
                }

                if(bFirstCell) {
                    if(includeFirstCell) {
                        range = new Range(r.getLeft(), r.getTop(), r.getRight(), r.getBottom());
                    }
                } else {
                    range = new Range(r.getLeft(), r.getTop(), r.getRight(), r.getBottom());
                }
                break;
            }
        }

        return range;
    }

    public int getMergedRangeCount() {
        return mergedRanges.size();
    }

    public boolean addMergedRange(Range range) {
        if(range != null) {
            if(isCrossFreezed(range)) {
                return false;
            }
            mergedRanges.add(range);
            ICellData cell = getCellData(range.getTop(), range.getLeft());
            if(cell != null) {
                cell.setMergedRange(range);
            }
        }

        return true;
    }

    private boolean isCrossFreezed(Range range) {
        if(isFreeze()) {
            int rowCount = getFreezedRowCount();
            if(range.getTop() < rowCount && range.getBottom() >= rowCount) {
                return true;
            }
            int colCount = getFreezedColCount();
            return range.getLeft() < colCount && range.getRight() >= rowCount;
        }

        return false;
    }

    public void removeMergedRangeAt(int index) {
        Range range = mergedRanges.remove(index);
        ICellData cell = getCellData(range.getTop(), range.getLeft());
        if(cell != null) {
            cell.setMergedRange(null);
        }
    }

    @Override
    public boolean isBlankCell(int rowIndex, int colIndex) {
        long cellPos = getCellPosition(rowIndex, colIndex);
        return cells.get(cellPos) == null;
    }

    @Override
    public int getLastRow() {
        return getMaxRowCount();
    }

    @Override
    public int getLastColumn() {
        return getMaxColumnCount();
    }

    @Override
    public int getLastColumn(int rowIndex) {
        return getMaxColumnCount(rowIndex);
    }

    @Override
    public int getMaxRowCount() {
        return maxRowCount;
    }

    public void setMaxRowCount(int rowCount) {
        maxRowCount = rowCount;
    }

    @Override
    public int getMaxColumnCount() {
        return maxColCount;
    }

    public void setMaxColumnCount(int colCount) {
        maxColCount = colCount;
    }

    @Override
    public int getMaxColumnCount(int rowIndex) {
        return getMaxColumnCount();
    }

    @Override
    public void setSheetStyleIndex(int index) {
        styleId = index;
    }

    @Override
    public int getSheetStyleIndex() {
        return styleId;
    }

    @Override
    public FontManager getFontManager() {
        return fontManager;
    }

    @Override
    public CellStyleManager getCellStyleManager() {
        return cellStyleManager;
    }

    @Override
    public int getGridLineColor() {
        return gridLineColor;
    }

    public void setGridLineColor(int color) {
        gridLineColor = color;
    }

    @Override
    public boolean isEmpty() {
        return cells.isEmpty();
    }

    private long getCellPosition(int rowIndex, int colIndex) {
        return ((long)rowIndex << 32) + colIndex;
    }

    private Range checkCellMerged(int rowIndex, int colIndex) {
        for(Range r : mergedRanges) {
            if( r.getTop() == rowIndex &&
                    r.getLeft() == colIndex) {
                return r;
            }
        }
        return null;
    }
}
