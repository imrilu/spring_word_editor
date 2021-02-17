package com.tomiaglobal.hw.Model;

import java.util.ArrayList;
import java.util.List;

public class State {

    private StringBuilder text;
    private List<Boolean> isBold, isItalic, isUnderline;

    public State(StringBuilder text, List<Boolean> isBold, List<Boolean> isItalic, List<Boolean> isUnderline) {
        this.text = new StringBuilder(text);
        this.isBold = new ArrayList<>(isBold);
        this.isItalic = new ArrayList<>(isItalic);
        this.isUnderline = new ArrayList<>(isUnderline);
    }

    public StringBuilder getText() {
        return text;
    }

    public List<Boolean> getIsBold() {
        return isBold;
    }

    public List<Boolean> getIsItalic() {
        return isItalic;
    }

    public List<Boolean> getIsUnderline() {
        return isUnderline;
    }
}
