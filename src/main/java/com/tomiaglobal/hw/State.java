package com.tomiaglobal.hw;

import java.util.ArrayList;
import java.util.List;

public class State {

    private StringBuilder text;
    private List<Boolean> isBold, isItalic, isUnderline;

    public State(StringBuilder text, List<Boolean> isBold, List<Boolean> isItalic, List<Boolean> isUnderline) {
        this.text = new StringBuilder(text);
        this.isBold = new ArrayList<>();
        this.isItalic = new ArrayList<>();
        this.isUnderline = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            this.isBold.add(isBold.get(i));
            this.isItalic.add(isItalic.get(i));
            this.isUnderline.add(isUnderline.get(i));
        }
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
