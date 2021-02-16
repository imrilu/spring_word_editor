package com.tomiaglobal.hw;

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
        clearRedo();
        text.append(s);
        for (int i = 0; i < s.length(); i++) {
            isBold.add(false);
            isItalic.add(false);
            isUnderline.add(false);
        }
    }

    public void add(String s, int position) {
        addUndo();
        clearRedo();
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
        if (fromPosition >= text.length() || toPosition > text.length() || fromPosition >= toPosition) {
            return;
        }
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
            assignState(redos.remove(0));
        }
    }

    public void undo() {
        if (!undos.empty()) {
            addRedo();
            assignState(undos.remove(0));
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

    private void addUndo() {
        undos.add(0, new State(text, isBold, isItalic, isUnderline));
    }

    private void addRedo() {
        redos.add(0, new State(text, isBold, isItalic, isUnderline));
    }

    private void clearRedo() {
        redos.clear();
    }

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

    private void assignState(State state) {
        text = new StringBuilder(state.getText());
        isItalic = new ArrayList<>(state.getIsItalic());
        isBold = new ArrayList<>(state.getIsBold());
        isUnderline = new ArrayList<>(state.getIsBold());
    }


}
