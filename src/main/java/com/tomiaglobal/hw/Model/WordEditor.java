package com.tomiaglobal.hw.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WordEditor {

    private StringBuilder text;
    private List<Boolean> isBold, isItalic, isUnderline;
    private Stack<State> undos, redos;

    public WordEditor() {
        text = new StringBuilder();
        isBold = new ArrayList<>();
        isItalic = new ArrayList<>();
        isUnderline = new ArrayList<>();
        undos = new Stack<>();
        redos = new Stack<>();
    }

    public void add(String s) {
        addUndo();
        redos.clear();
        text.append(s);
        for (int i = 0; i < s.length(); i++) {
            isBold.add(false);
            isItalic.add(false);
            isUnderline.add(false);
        }
    }

    public void add(String s, int position) {
        addUndo();
        redos.clear();
        if (position > text.length())
            return;
        text.insert(position, s);
        for (int i = position; i < position + s.length(); i++) {
            isBold.add(i, false);
            isItalic.add(i, false);
            isUnderline.add(i, false);
        }
    }

    public void remove(int fromPosition, int toPosition) {
        addUndo();
        if (fromPosition >= text.length() || toPosition > text.length() || fromPosition >= toPosition)
            return;
        text.delete(fromPosition, toPosition);
        for (int i = fromPosition; i < toPosition; i++) {
            isBold.remove(fromPosition);
            isItalic.remove(fromPosition);
            isUnderline.remove(fromPosition);
        }
    }

    public void italic(int fromPosition, int toPosition) {
        addUndo();
        setBoolList(fromPosition, toPosition, isItalic);
    }

    public void bold(int fromPosition, int toPosition) {
        addUndo();
        setBoolList(fromPosition, toPosition, isBold);
    }

    public void underline(int fromPosition, int toPosition) {
        addUndo();
        setBoolList(fromPosition, toPosition, isUnderline);
    }

    public void redo() {
        if (!redos.empty()) {
            addUndo();
            loadState(redos.remove(0));
        }
    }

    public void undo() {
        if (!undos.empty()) {
            addRedo();
            loadState(undos.remove(0));
        }
    }

    public String print() {
        StringBuilder str = new StringBuilder();
        boolean isCurBold = false, isCurItalic = false, isCurUnderline = false;
        for (int i = 0; i < text.length(); i++) {
            if (isBold.get(i) && !isCurBold) {
                isCurBold = true;
                str.append("<b>");
            } else if (!isBold.get(i) && isCurBold) {
                isCurBold = false;
                str.append("</b>");
            }
            if (isItalic.get(i) && !isCurItalic) {
                isCurItalic = true;
                str.append("<i>");
            } else if (!isItalic.get(i) && isCurItalic) {
                isCurItalic = false;
                str.append("</i>");
            }
            if (isUnderline.get(i) && !isCurUnderline) {
                isCurUnderline = true;
                str.append("<u>");
            } else if (!isUnderline.get(i) && isCurUnderline) {
                isCurUnderline = false;
                str.append("</u>");
            }
            str.append(text.charAt(i));
        }
        if (text.length() > 0 && isBold.get(text.length() - 1) && isCurBold)
            str.append("</b>");
        if (text.length() > 0 && isItalic.get(text.length() - 1) && isCurItalic)
            str.append("</i>");
        if (text.length() > 0 && isUnderline.get(text.length() - 1) && isCurUnderline)
            str.append("</u>");
        return str.toString();
    }

    /**
     * Helper method to add word editor's current state to undo list
     */
    private void addUndo() {
        undos.add(0, new State(text, isBold, isItalic, isUnderline));
    }

    /**
     * Helper method to add word editor's current state to redo list
     */
    private void addRedo() {
        redos.add(0, new State(text, isBold, isItalic, isUnderline));
    }

    /**
     * Helper method to load the input state into current instance
     * @param state The state to load into this instance
     */
    private void loadState(State state) {
        text = new StringBuilder(state.getText());
        isItalic = new ArrayList<>(state.getIsItalic());
        isBold = new ArrayList<>(state.getIsBold());
        isUnderline = new ArrayList<>(state.getIsUnderline());
    }

    /**
     * Helper method to switch boolean values on given list, from true to false and vice versa,
     * from indices 'fromPosition' to 'toPosition'
     * @param fromPosition start index
     * @param toPosition end index
     * @param list the list to perform the switch
     */
    private void setBoolList(int fromPosition, int toPosition, List<Boolean> list) {
        if (fromPosition >= text.length() && toPosition >= text.length())
            return;
        boolean value;
        for (int i = fromPosition; i < toPosition; i++) {
            value = list.remove(i);
            if (value) {
                list.add(i, false);
            } else {
                list.add(i, true);
            }
        }
    }

}
