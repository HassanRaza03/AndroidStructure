package com.android.structure.libraries.table.model.style;

import com.android.structure.libraries.table.util.Objects;

public class TableBorderLines {

    public BorderLineStyle leftBorderLine;
    public BorderLineStyle topBorderLine;
    public BorderLineStyle rightBorderLine;
    public BorderLineStyle bottomBorderLine;

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        if (leftBorderLine != null) {
            buf.append("leftBorderLine:" + leftBorderLine.getType() + ",");
            buf.append(leftBorderLine.getColor() + ",");
            buf.append(leftBorderLine.getWidth() + ";");
        }
        if (topBorderLine != null) {
            buf.append("topBorderLine:" + topBorderLine.getType() + ",");
            buf.append(topBorderLine.getColor() + ",");
            buf.append(topBorderLine.getWidth() + ";");
        }
        if (rightBorderLine != null) {
            buf.append("rightBorderLine:" + rightBorderLine.getType() + ",");
            buf.append(rightBorderLine.getColor() + ",");
            buf.append(rightBorderLine.getWidth() + ";");
        }
        if (bottomBorderLine != null) {
            buf.append("bottomBorderLine:" + bottomBorderLine.getType() + ",");
            buf.append(bottomBorderLine.getColor() + ",");
            buf.append(bottomBorderLine.getWidth() + ";");
        }

        return buf.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof TableBorderLines)) {
            return false;
        }

        if(this == o) {
            return true;
        }

        TableBorderLines obj = (TableBorderLines) o;
        return Objects.equals(leftBorderLine, obj.leftBorderLine)
                && Objects.equals(rightBorderLine, obj.rightBorderLine)
                && Objects.equals(topBorderLine, obj.topBorderLine)
                && Objects.equals(bottomBorderLine, obj.bottomBorderLine);

    }
}
